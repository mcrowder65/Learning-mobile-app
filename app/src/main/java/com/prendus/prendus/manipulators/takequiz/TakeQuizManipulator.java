package com.prendus.prendus.manipulators.takequiz;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prendus.prendus.async.AsyncResponse;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.objects.question.Question;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
        try {
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
        } catch (Exception e) {
            Utilities.log(e);
        }

    }

    @Override
    public void update() {

    }

    @Override
    public void manipulate() {
        try {
            this.quizTitle.setText(this.quiz.getTitle());
            this.callGetQuestion();
        } catch (Exception e) {
            Utilities.log(e);
        }


    }

    public void nextQuestion() {
        try {
            this.gradeQuestion();
            this.callGetQuestion();
        } catch (Exception e) {
            Utilities.log(e);
        }

    }

    private void gradeQuestion() {
        try {
            //TODO check answer.
            String userAnswerAsStr = String.valueOf(userQuizAnswer.getText());
            boolean correct = isQuestionCorrect(userAnswerAsStr);
            if (correct) {
                ++numRight;
            }
        } catch (Exception e) {
            Utilities.log(e);
        }

    }

    private boolean isQuestionCorrect(String userAnswerAsStr) {
        return this.currentQuestion.getAnswer().equals(Utilities.stripEverything(userAnswerAsStr));
    }

    private void callGetQuestion() {
        try {
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
        } catch (Exception e) {
            Utilities.log(e);
        }


    }

    private void gradeQuiz() {
        try {
            double finalGrade = (double) numRight / (double) this.questionIds.length;
            Utilities.log("final grade: " + finalGrade);
            double percentage = finalGrade * 100;
            quizResults.setText(" You scored: " + percentage + "%");
            this.nextQuestion.setEnabled(false);
        } catch (Exception e) {
            Utilities.log(e);
        }

    }

    @Override
    public void processFinish(String output) {
        try {
            Question question = Utilities.g.fromJson(output, Question.class);
            this.quizQuestion.setText(question != null ? question.getQuestion() : "SERVER DOWN!!!");
            this.currentQuestion = question;
        } catch (Exception e) {
            Utilities.log(e);
        }

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
            OutputStream os = null;
            // Send data
            Question question = null;
            try {


                String urlStr = "http://138.68.44.195:5000/getQuestion?questionId=" + questionId;
                URL url = new URL(urlStr);
                conn = url.openConnection();
                conn.setDoOutput(false);


                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                text = sb.toString();
                if (text.indexOf("A#8t&9u@dCfZAG8") == -1) {
                    Question questionWrapper = Utilities.g.fromJson(text, Question.class);
                    question = questionWrapper;
                }


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
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                    Utilities.log(e);
                }
            }
            if (question == null) {
                return "";
            }
            return question.toJson();
        }

        protected void onPostExecute(String str) {
            delegate.processFinish(str);
        }
    }
}
