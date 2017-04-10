package com.prendus.prendus.activities.filter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;

/**
 * Created by mcrowder65 on 4/10/17.
 */

public class FilterActivity extends PrendusActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
    }
}
