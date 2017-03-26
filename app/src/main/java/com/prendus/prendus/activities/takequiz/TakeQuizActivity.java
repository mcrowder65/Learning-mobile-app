package com.prendus.prendus.activities.takequiz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/19/17.
 */

public class TakeQuizActivity extends PrendusActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Utilities.hideSpinner(this);
    }

}
