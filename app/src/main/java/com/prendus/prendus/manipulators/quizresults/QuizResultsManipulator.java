package com.prendus.prendus.manipulators.quizresults;

import android.content.Intent;

import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.constants.IntentExtras;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.questionresult.QuestionResult;
import com.prendus.prendus.utilities.Utilities;

import java.util.List;

/**
 * Created by mcrowder65 on 4/9/17.
 */

public class QuizResultsManipulator implements IPrendusManipulator {
    private PrendusActivity quizResultsActivity;
    private List<QuestionResult> questionResults;
    private double finalGrade;

    public QuizResultsManipulator(PrendusActivity quizResultsActivity) {
        this.quizResultsActivity = quizResultsActivity;
        questionResults = Utilities.g.fromJson(getIntent().getStringExtra(IntentExtras.QUIZ_RESULTS_LIST), List.class);
        finalGrade = getIntent().getDoubleExtra(IntentExtras.FINAL_GRADE, -1);
    }

    private Intent getIntent() {
        return quizResultsActivity.getIntent();
    }

    @Override
    public void update() {

    }

    @Override
    public void manipulate() {

    }
}
