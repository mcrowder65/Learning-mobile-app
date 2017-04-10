package com.prendus.prendus.activities.takequiz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.constants.IntentExtras;
import com.prendus.prendus.manipulators.takequiz.TakeQuizManipulator;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/19/17.
 */

public class TakeQuizActivity extends PrendusActivity {
    public Quiz quiz;
    public TextView quizTitle;
    public TextView quizQuestion;
    public EditText answer;
    public TakeQuizManipulator takeQuizManipulator;
    public Button nextQuestion;
    public TextView quizResults;
    public TextView currentQuestionNumber;
    public ImageView thumbUp;
    public ImageView thumbDown;
    public TextView quizScore;
    public int quizScoreAsNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        String quizAsJson = getIntent().getStringExtra(IntentExtras.QUIZ_AS_JSON);
        quiz = Utilities.g.fromJson(quizAsJson, Quiz.class);
        if (!quizAsJson.equals(quiz.toJson())) {
            Utilities.log("Something is going horribly wrong in take quiz...");
        }
        quizTitle = (TextView) findViewById(R.id.quizTitle);
        quizQuestion = (TextView) findViewById(R.id.quizQuestion);
        nextQuestion = (Button) findViewById(R.id.nextQuestion);
        quizResults = (TextView) findViewById(R.id.quizResults);
        answer = (EditText) findViewById(R.id.answer);
        currentQuestionNumber = (TextView) findViewById(R.id.currentQuestionNumber);
        thumbUp = (ImageView) findViewById(R.id.thumbup);
        thumbDown = (ImageView) findViewById(R.id.thumbdown);
        quizScore = (TextView) findViewById(R.id.quizScore);
        TakeQuizActivity self = this;
        takeQuizManipulator = new TakeQuizManipulator(self);
        takeQuizManipulator.manipulate();
    }

    public void nextQuestionClicked(View view) {

        takeQuizManipulator.nextQuestion();
    }

}
