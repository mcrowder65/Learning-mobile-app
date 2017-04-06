package com.prendus.prendus.manipulators.takequiz;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import com.prendus.prendus.activities.takequiz.TakeQuizActivity;
import com.prendus.prendus.async.AsyncResponse;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.objects.question.Question;
import com.prendus.prendus.questionresult.QuestionResult;
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
import java.util.List;

/**
 * Created by matt on 3/26/17.
 */

public class TakeQuizManipulator implements IPrendusManipulator, AsyncResponse {
    private int currentQuestionIndex;
    private String[] questionIds;
    private GetQuestion asyncQuestionGetter;
    private Question currentQuestion;
    private int numRight;
    private List<QuestionResult> questionResults;
    private TakeQuizActivity takeQuizActivity;

    public TakeQuizManipulator(TakeQuizActivity takeQuizActivity) {
        try {
            this.takeQuizActivity = takeQuizActivity;
            this.questionIds = takeQuizActivity.quiz.getQuestionIds();
            this.currentQuestionIndex = 0;
            numRight = 0;
            takeQuizActivity.quizResults.setText("");
        } catch (Exception e) {
            Utilities.log(e);
        }

    }

    @Override
    public void update() {

    }

    private void setQuestionNumberIndex() {
        int num = currentQuestionIndex == 0 ? 1 : currentQuestionIndex;
        takeQuizActivity.currentQuestionNumber.setText("Question " + num + " out of " + this.questionIds.length);
    }

    @Override
    public void manipulate() {
        try {
            takeQuizActivity.quizTitle.setText(takeQuizActivity.quiz.getTitle());
            this.setQuestionNumberIndex();
            this.callGetQuestion();
        } catch (Exception e) {
            Utilities.log(e);
        }


    }

    public void nextQuestion() {
        try {
            this.gradeQuestion();
            this.callGetQuestion();
            this.setQuestionNumberIndex();
        } catch (Exception e) {
            Utilities.log(e);
        }

    }

    private void gradeQuestion() {
        try {
            String userAnswerAsStr = String.valueOf(takeQuizActivity.answer.getText());


            boolean correct = isQuestionCorrect(userAnswerAsStr);
            QuestionResult questionResult = new QuestionResult(currentQuestion, userAnswerAsStr, correct);
            if (correct) {
                ++numRight;
            }
            questionResults.add(questionResult);
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
                asyncQuestionGetter.execute(takeQuizActivity.quiz.getId(), questionId);
                if (this.currentQuestionIndex == this.questionIds.length) {
                    // call this here because currentQuestionIndex is incremented everytime... and the
                    // first if can never be entered again!
                    takeQuizActivity.nextQuestion.setText("submit");
                }
                takeQuizActivity.answer.setText("");
            } else {
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
            takeQuizActivity.quizResults.setText(" You scored: " + percentage + "%");
            takeQuizActivity.nextQuestion.setEnabled(false);
            //TODO go to quiz results page
        } catch (Exception e) {
            Utilities.log(e);
        }

    }

    @Override
    public void processFinish(String output) {
        try {
            Question question = Utilities.g.fromJson(output, Question.class);
            takeQuizActivity.quizQuestion.setText(question != null ? question.getQuestion() : "SERVER DOWN!!!");
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
