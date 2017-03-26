package com.prendus.prendus.activities.takequiz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.constants.IntentExtras;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/19/17.
 */

public class TakeQuizActivity extends PrendusActivity {
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Utilities.hideSpinner(this);
        String quizAsJson = getIntent().getStringExtra(IntentExtras.QUIZ_AS_JSON);
        quiz = Utilities.g.fromJson(quizAsJson, Quiz.class);
        if (!quizAsJson.equals(quiz.toJson())) {
            Utilities.log("Something is going horribly wrong in take quiz...");
        }
    }

}
