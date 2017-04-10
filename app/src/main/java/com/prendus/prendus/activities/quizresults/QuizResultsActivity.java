package com.prendus.prendus.activities.quizresults;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.manipulators.quizresults.QuizResultsManipulator;
import com.prendus.prendus.questionresult.QuestionResult;

import java.util.List;

/**
 * Created by mcrowder65 on 4/5/17.
 */

public class QuizResultsActivity extends PrendusActivity {
    private List<QuestionResult> questionResults;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView finalGrade;

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
        QuizResultsManipulator manipulator = new QuizResultsManipulator(this);
        manipulator.manipulate();

    }

    public void setFinalGrade(double grade) {
        if (finalGrade == null) {
            finalGrade = (TextView) findViewById(R.id.quizscore);
        }
        double percentage = grade * 100;
        finalGrade.setText("Final score: " + String.valueOf(percentage) + "%");
    }

    public void setAdapter(QuizResultsRVAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
}
