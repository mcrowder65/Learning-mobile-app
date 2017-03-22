package com.prendus.prendus.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.IPrendusActivity;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.activities.search.SearchActivity;
import com.prendus.prendus.activities.signup.SignupActivity;
import com.prendus.prendus.constants.MenuOptions;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/19/17.
 */

public class ProfileActivity extends AppCompatActivity implements IPrendusActivity {
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        title = (TextView)findViewById(R.id.profiletitle);
        title.setText("profile");

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
        PopupMenu popup = new PopupMenu(ProfileActivity.this, menuItemView);
        Utilities.populatePopup(popup);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        final ProfileActivity self = this;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Utilities.navigate(item, self, MenuOptions.PROFILE);
                return true;
            }
        });
        popup.show();//showing popup menu
    }
}
