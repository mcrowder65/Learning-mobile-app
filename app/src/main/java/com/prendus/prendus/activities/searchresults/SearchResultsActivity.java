package com.prendus.prendus.activities.searchresults;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.IPrendusActivity;
import com.prendus.prendus.activities.search.SearchActivity;
import com.prendus.prendus.manipulators.searchresults.SearchResultsManipulator;
import com.prendus.prendus.utilities.Utilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcrowder65 on 3/23/17.
 */

public class SearchResultsActivity extends AppCompatActivity implements IPrendusActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Utilities.hideSpinner(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        SearchResultsManipulator manipulator = new SearchResultsManipulator(mRecyclerView);
        manipulator.manipulate();

    }
    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void dropDownMenuClicked(MenuItem item) {
        View menuItemView = findViewById(R.id.menu);
        PopupMenu popup = new PopupMenu(SearchResultsActivity.this, menuItemView);
        Utilities.populatePopup(popup);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        final SearchResultsActivity self = this;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Utilities.navigate(item, self, null);
                return true;
            }
        });
        popup.show();//showing popup menu
    }

    @Override
    public void searchClicked(MenuItem item) {
        Utilities.goToActivity(SearchActivity.class, this);
    }

    @Override
    public ProgressBar getSpinner() {

        ProgressBar spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        return spinner;
    }


}
