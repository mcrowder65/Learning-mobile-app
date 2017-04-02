package com.prendus.prendus.activities.search;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.activities.searchresults.SearchResultsActivity;
import com.prendus.prendus.constants.IntentExtras;
import com.prendus.prendus.utilities.Utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matt on 3/19/17.
 */

public class SearchActivity extends PrendusActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
    }


    @Override
    public void searchClicked(MenuItem item) {
    }

    private EditText searchText;

    public void goClicked(View view) {
        try {
            searchText = (EditText) findViewById(R.id.search_text_input);
            String searchStr = String.valueOf(searchText.getText());
            if (searchStr != null && !searchStr.equals("")) {
                Map<String, String> stringExtras = new HashMap<>();
                stringExtras.put(IntentExtras.SEARCH_INPUT, searchStr);
                Utilities.goToActivity(SearchResultsActivity.class, this, stringExtras);
            }

        } catch (Exception e) {
            Utilities.log(e.toString());
        }

    }
}
