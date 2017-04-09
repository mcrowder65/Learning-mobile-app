package com.prendus.prendus.activities.quizresults;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.manipulators.quizresults.QuizResultsManipulator;
import com.prendus.prendus.questionresult.QuestionResult;
import com.prendus.prendus.utilities.Utilities;

import java.util.List;

/**
 * Created by mcrowder65 on 4/5/17.
 */

public class QuizResultsActivity extends PrendusActivity {
    private List<QuestionResult> questionResults;
    private double finalGrade;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        if (finalGrade == -1) {
            Utilities.log("something is going terribly wrong in the quiz results activity");
        }
        QuizResultsManipulator manipulator = new QuizResultsManipulator(this);
        manipulator.manipulate();

    }

}
