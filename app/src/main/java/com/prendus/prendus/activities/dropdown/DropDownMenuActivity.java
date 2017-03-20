package com.prendus.prendus.activities.dropdown;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcrowder65 on 3/20/17.
 */

public class DropDownMenuActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown_menu);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> items = new ArrayList<>();
        if(auth.getCurrentUser() != null) {
            Log.wtf(Constants.TAG, auth.getCurrentUser().getEmail());

            items.add("My Quizzes");
            items.add("Profile");
            items.add("Logout");

        } else {
            items.add("Login");
            items.add("Signup");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.wtf(Constants.TAG, "item selected! " + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
