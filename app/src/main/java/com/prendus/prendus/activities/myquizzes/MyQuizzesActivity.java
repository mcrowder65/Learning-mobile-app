package com.prendus.prendus.activities.myquizzes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/19/17.
 */

public class MyQuizzesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quizzes);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
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
        PopupMenu popup = new PopupMenu(MyQuizzesActivity.this, menuItemView);
        if(Utilities.isLoggedIn()) {
            popup.getMenu().add("My Quizzes");
            popup.getMenu().add("Profile");
            popup.getMenu().add("Logout");
        } else {
            popup.getMenu().add("Login");
            popup.getMenu().add("Sign up");
        }
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(Utilities.isLoggedIn()) {
                    switch((String)item.getTitle()) {
                        case "My Quizzes": {
                            Toast.makeText(MyQuizzesActivity.this,"You're already on my quizzes!",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "Profile": {
                            //TODO go to profile
                            break;
                        }
                        case "Logout": {
                            Utilities.logout();
                            break;
                        }
                        default: {
                            Toast.makeText(MyQuizzesActivity.this,"You didn't initialize a case",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    switch((String)item.getTitle()) {
                        case "Login": {
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            break;
                        }

                        case "Sign up": {
                            //TODO go to signup
                            break;
                        }
                        default: {
                            Toast.makeText(MyQuizzesActivity.this,"You didn't initialize a case",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                Toast.makeText(MyQuizzesActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();//showing popup menu
    }
}
