package com.prendus.prendus.activities.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.activities.search.SearchActivity;
import com.prendus.prendus.constants.MenuOptions;
import com.prendus.prendus.manipulators.profile.ProfileManipulator;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/19/17.
 */

public class ProfileActivity extends PrendusActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText institution;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        Utilities.hideSpinner(this);


        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        institution = (EditText)findViewById(R.id.institution);
        email = (EditText)findViewById(R.id.email);


        ProfileManipulator manipulator = new ProfileManipulator(firstName, lastName, institution, email);
        manipulator.manipulate();


    }


    public void submit(View view) {
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        institution = (EditText)findViewById(R.id.institution);
        email = (EditText)findViewById(R.id.email);
        ProfileManipulator manipulator = new ProfileManipulator(firstName, lastName, institution, email);
        manipulator.update();
    }

}
