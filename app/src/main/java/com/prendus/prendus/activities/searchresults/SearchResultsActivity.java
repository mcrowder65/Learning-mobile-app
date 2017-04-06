package com.prendus.prendus.activities.searchresults;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.manipulators.searchresults.SearchResultsManipulator;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by mcrowder65 on 3/23/17.
 */

public class SearchResultsActivity extends PrendusActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView searchInput;
    public TextView noQuizzesYetText;
    private SearchResultsManipulator manipulator;

    public SearchResultsManipulator getManipulator() {
        if (manipulator == null) {
            Utilities.log("Why the freak is this manipulator null?!?!?!?!?!");
        }
        return manipulator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_results);

            // Find the toolbar view inside the activity layout
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            // Sets the Abstract to act as the ActionBar for this Activity window.
            // Make sure the toolbar exists in the activity and is not null
            setSupportActionBar(toolbar);
            searchInput = (TextView) findViewById(R.id.userSearchInput);
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLinearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            noQuizzesYetText = (TextView) findViewById(R.id.noquizzesyet);
            manipulator = new SearchResultsManipulator(mRecyclerView, searchInput, getIntent(), this);
            manipulator.manipulate();
        } catch (Exception e) {
            Utilities.log(e);
        }


    }

}
