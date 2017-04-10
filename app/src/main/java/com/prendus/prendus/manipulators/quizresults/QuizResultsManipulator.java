package com.prendus.prendus.manipulators.quizresults;

import android.content.Intent;

import com.google.gson.internal.LinkedTreeMap;
import com.prendus.prendus.activities.quizresults.QuizResultsActivity;
import com.prendus.prendus.activities.quizresults.QuizResultsData;
import com.prendus.prendus.activities.quizresults.QuizResultsRVAdapter;
import com.prendus.prendus.constants.IntentExtras;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.questionresult.QuestionResult;
import com.prendus.prendus.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcrowder65 on 4/9/17.
 */

public class QuizResultsManipulator implements IPrendusManipulator {
    private QuizResultsActivity quizResultsActivity;
    private List<LinkedTreeMap> linkedTreeMaps;
    private List<QuestionResult> questionResults;
    private double finalGrade;


    public QuizResultsManipulator(QuizResultsActivity quizResultsActivity) {
        this.quizResultsActivity = quizResultsActivity;
        linkedTreeMaps = Utilities.g.fromJson(getIntent().getStringExtra(IntentExtras.QUIZ_RESULTS_LIST), List.class);
        questionResults = new ArrayList<>();
        for (int i = 0; i < linkedTreeMaps.size(); i++) {
            LinkedTreeMap map = linkedTreeMaps.get(i);
            QuestionResult result = new QuestionResult(map);
            questionResults.add(result);
        }
        finalGrade = getIntent().getDoubleExtra(IntentExtras.FINAL_GRADE, -1);
        if (finalGrade == -1) {
            Utilities.log("Something is going terribly wrong in the quiz results " +
                    "activity/manipulator with the final grade");
        }
        quizResultsActivity.setFinalGrade(finalGrade);


    }

    private List<QuestionResult> convertToQuestionResults() {
        return null;
    }

    private Intent getIntent() {
        return quizResultsActivity.getIntent();
    }


    @Override
    public void manipulate() {
        List<QuizResultsData> data = new ArrayList<>();
        for (int i = 0; i < questionResults.size(); i++) {
            QuestionResult r = questionResults.get(i);
            data.add(new QuizResultsData(r.getQuestion().getQuestion(), r.getUserAnswer(),
                    r.getQuestion().getAnswer(), r.isCorrect()));
        }
        QuizResultsRVAdapter adapter = new QuizResultsRVAdapter(data, quizResultsActivity);
        quizResultsActivity.setAdapter(adapter);
    }
}
