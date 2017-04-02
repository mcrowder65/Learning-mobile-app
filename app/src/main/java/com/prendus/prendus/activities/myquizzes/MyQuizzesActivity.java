package com.prendus.prendus.activities.myquizzes;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.manipulators.myquizzes.MyQuizzesManipulator;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/19/17.
 */

public class MyQuizzesActivity extends PrendusActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView searchInput;
    private MyQuizzesManipulator manipulator;

    public MyQuizzesManipulator getManipulator() {
        return manipulator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quizzes);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Utilities.hideSpinner(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        manipulator = new MyQuizzesManipulator(mRecyclerView, getIntent(), this);
        manipulator.manipulate();
    }

}
