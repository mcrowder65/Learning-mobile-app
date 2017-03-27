package com.prendus.prendus.manipulators.takequiz;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.prendus.prendus.async.AsyncResponse;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.objects.question.Question;
import com.prendus.prendus.objects.question.QuestionWrapper;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by matt on 3/26/17.
 */

public class TakeQuizManipulator implements IPrendusManipulator, AsyncResponse {
    private TextView quizTitle;
    private TextView quizQuestion;
    private EditText userQuizAnswer;
    private Quiz quiz;
    private int currentQuestionIndex;
    private String[] questionIds;
    private GetQuestion asyncQuestionGetter;
    private Button nextQuestion;
    private Question currentQuestion;
    private TextView quizResults;
    private int numRight;

    public TakeQuizManipulator(TextView quizTitle, TextView quizQuestion, Quiz quiz,
                               Button nextQuestion, EditText userQuizAnswer, TextView quizResults) {
        this.quizTitle = quizTitle;
        this.quizQuestion = quizQuestion;
        this.quiz = quiz;
        this.questionIds = quiz.getQuestionIds();
        this.currentQuestionIndex = 0;
        this.nextQuestion = nextQuestion;
        this.userQuizAnswer = userQuizAnswer;
        numRight = 0;
        this.quizResults = quizResults;
        quizResults.setText("");
    }

    @Override
    public void update() {

    }

    @Override
    public void manipulate() {
        this.quizTitle.setText(this.quiz.getTitle());
        this.callGetQuestion();


    }

    public void nextQuestion() {
        this.gradeQuestion();
        this.callGetQuestion();
    }

    private void gradeQuestion() {
        //TODO check answer.
        String userAnswerAsStr = String.valueOf(userQuizAnswer.getText());
        boolean correct = isQuestionCorrect(userAnswerAsStr);
        if (correct) {
            ++numRight;
        }
    }

    private boolean isQuestionCorrect(String userAnswerAsStr) {
        return this.currentQuestion.getAnswer().equals(Utilities.stripEverything(userAnswerAsStr));
    }

    private void callGetQuestion() {

        if (this.currentQuestionIndex < this.questionIds.length) {
            //increment currentQuestionIndex for the next time you want it.
            String questionId = this.questionIds[this.currentQuestionIndex++];

            asyncQuestionGetter = new GetQuestion();
            asyncQuestionGetter.delegate = this;
            asyncQuestionGetter.execute(quiz.getId(), questionId);
            if (this.currentQuestionIndex == this.questionIds.length) {
                // call this here because currentQuestionIndex is incremented everytime... and the
                // first if can never be entered again!
                this.nextQuestion.setText("submit");
            }
            this.userQuizAnswer.setText("");
        } else {
            //TODO grade
            this.gradeQuiz();
        }


    }

    private void gradeQuiz() {
        double finalGrade = (double) numRight / (double) this.questionIds.length;
        Utilities.log("final grade: " + finalGrade);
        double percentage = (1 - finalGrade) * 100;
        quizResults.setText(" You scored: " + percentage + "%");
    }

    @Override
    public void processFinish(String output) {

        Question question = Utilities.g.fromJson(output, Question.class);
        this.quizQuestion.setText(question.getQuestion());
        this.currentQuestion = question;
    }

    class GetQuestion extends AsyncTask<String, Void, String> {
        public AsyncResponse delegate = null;

        @Override
        protected String doInBackground(String... strings) {
            String quizId = strings[0];
            String questionId = strings[1];
            // Create data variable for sent values to server
            URLConnection conn = null;
            BufferedReader reader = null;
            String text = null;
            OutputStreamWriter wr = null;
            // Send data
            Question question = null;
            try {

                // Defined URL  where to send data

                Task jwtTask = FirebaseAuth.getInstance().getCurrentUser().getToken(false);

                // Send POST data request
                GetTokenResult result = (GetTokenResult) jwtTask.getResult();
                String jwt = result.getToken();
                String urlStr = "http://10.37.32.196:5000/api/jwt/" + jwt + "/quiz/" + quizId + "/question/" + questionId;
                URL url = new URL(urlStr);
                conn = url.openConnection();
                conn.setDoOutput(false);

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                text = sb.toString();
                QuestionWrapper questionWrapper = Utilities.g.fromJson(text, QuestionWrapper.class);
                question = questionWrapper.getQuestion();

            } catch (NetworkOnMainThreadException e) {
                Utilities.log(e);
            } catch (MalformedURLException e) {
                Utilities.log(e);
            } catch (EOFException e) {
                Utilities.log(e);
            } catch (IOException e) {
                Utilities.log(e);
            } catch (Exception e) {
                Utilities.log(e);
            }
            return question.toJson();
        }

        protected void onPostExecute(String str) {
            delegate.processFinish(str);
        }
    }
}
