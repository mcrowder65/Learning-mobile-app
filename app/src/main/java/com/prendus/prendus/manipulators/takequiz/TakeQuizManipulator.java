package com.prendus.prendus.manipulators.takequiz;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.objects.question.Question;
import com.prendus.prendus.objects.question.QuestionWrapper;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

import org.jsoup.Jsoup;

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

public class TakeQuizManipulator implements IPrendusManipulator {
    private TextView quizTitle;
    private TextView quizQuestion;
    private Quiz quiz;
    private int currentQuestionIndex;
    private String[] questionIds;

    public TakeQuizManipulator(TextView quizTitle, TextView quizQuestion, Quiz quiz) {
        this.quizTitle = quizTitle;
        this.quizQuestion = quizQuestion;
        this.quiz = quiz;
        this.questionIds = quiz.getQuestionIds();
        this.currentQuestionIndex = 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void manipulate() {
        this.quizTitle.setText(this.quiz.getTitle());
        String questionId = this.questionIds[this.currentQuestionIndex];

        new GetQuestion().execute(quiz.getId(), questionId);
    }

    public void nextQuestion() {

    }

    class GetQuestion extends AsyncTask<String, Void, String> {

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
            try {

                // Defined URL  where to send data

                Task jwtTask = FirebaseAuth.getInstance().getCurrentUser().getToken(false);

                // Send POST data request
                GetTokenResult result = (GetTokenResult) jwtTask.getResult();
                String jwt = result.getToken();
                String urlStr = "http://10.10.144.123:5000/api/jwt/" + jwt + "/quiz/" + quizId + "/question/" + questionId;
                URL url = new URL(urlStr);
                conn = url.openConnection();
                conn.setDoOutput(false);
//                wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//                wr.write("i am matt");
//                wr.flush();

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
                Question question = questionWrapper.getQuestion();
                Utilities.log(question);
                String questionText = Jsoup.parse(question.getQuestion()).text();
                Utilities.log(questionText);
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
            } finally {
//                try {
//                    wr.close();
//                } catch (IOException e) {
//                    Utilities.log(e);
//                }
            }
            return text;
        }

        protected void onPostExecute(String str) {

        }
    }
}
