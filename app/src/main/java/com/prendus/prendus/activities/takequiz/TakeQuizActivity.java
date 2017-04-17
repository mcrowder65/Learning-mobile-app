package com.prendus.prendus.activities.takequiz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

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
    public ViewAnimator simpleViewAnimator;
    Animation slide_in_left, slide_out_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
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
            simpleViewAnimator = (ViewAnimator) findViewById(R.id.simpleViewAnimator); //get the reference of ViewAnimator

            slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

            simpleViewAnimator.setInAnimation(slide_out_right);
            simpleViewAnimator.setOutAnimation(slide_out_right);
        } catch (Exception e) {
            Utilities.log(e);
        }

    }

    public void nextQuestionClicked(View view) {
        try {
            simpleViewAnimator.showNext();
            takeQuizManipulator.nextQuestion();
        } catch (Exception e) {
            Utilities.log(e);
        }
    }

}
