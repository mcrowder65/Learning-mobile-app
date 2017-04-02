package com.prendus.prendus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.utilities.Utilities;


public class MainActivity extends PrendusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        makeSnackBar("init!");
        Utilities.hideSpinner(this);
    }


    public void movingToLogin(MenuItem item) {
        makeSnackBar("moving to login!");
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

}
