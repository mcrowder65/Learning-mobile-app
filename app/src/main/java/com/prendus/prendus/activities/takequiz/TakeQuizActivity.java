package com.prendus.prendus.activities.takequiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.IPrendusActivity;
import com.prendus.prendus.activities.MainActivity;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.activities.profile.ProfileActivity;
import com.prendus.prendus.activities.search.SearchActivity;
import com.prendus.prendus.activities.signup.SignupActivity;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/19/17.
 */

public class TakeQuizActivity extends AppCompatActivity implements IPrendusActivity {
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        title = (TextView) findViewById(R.id.takequiztitle);
        title.setText("take quiz");
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void dropDownMenuClicked(MenuItem item) {
        View menuItemView = findViewById(R.id.menu);
        PopupMenu popup = new PopupMenu(TakeQuizActivity.this, menuItemView);
        Utilities.populatePopup(popup);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        final TakeQuizActivity self = this;
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
