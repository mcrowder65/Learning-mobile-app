package com.prendus.prendus.activities.searchresults;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
            Utilities.hideSpinner(this);
            searchInput = (TextView) findViewById(R.id.userSearchInput);
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLinearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            SearchResultsManipulator manipulator = new SearchResultsManipulator(mRecyclerView, searchInput, getIntent(), this);
            manipulator.manipulate();
        } catch (Exception e) {
            Utilities.log(e);
        }


    }

    public void starClick(View view) {
        Utilities.log("star clicked!");
    }


}
