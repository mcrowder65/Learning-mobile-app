package com.prendus.prendus.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.prendus.prendus.R;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.activities.profile.ProfileActivity;
import com.prendus.prendus.activities.signup.SignupActivity;
import com.prendus.prendus.activities.takequiz.TakeQuizActivity;
import com.prendus.prendus.utilities.Utilities;


public class MainActivity extends AppCompatActivity {

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
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void makeSnackBar(String message) {
        Snackbar mySnackBar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), message, Snackbar.LENGTH_SHORT);
        mySnackBar.show();
    }
    public void movingToLogin(MenuItem item) {
        makeSnackBar("moving to login!");
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void dropDownMenuClicked(MenuItem item) {
        View menuItemView = findViewById(R.id.menu);
        PopupMenu popup = new PopupMenu(MainActivity.this, menuItemView);
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
                            Intent i = new Intent(getApplicationContext(), MyQuizzesActivity.class);
                            startActivity(i);
                            break;
                        }
                        case "Profile": {
                            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(i);
                            break;
                        }
                        case "Logout": {
                            Utilities.logout();
                            break;
                        }
                        default: {
                            Toast.makeText(MainActivity.this,"You didn't initialize a case",Toast.LENGTH_SHORT).show();
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
                            Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                            startActivity(i);
                            break;
                        }
                        default: {
                            Toast.makeText(MainActivity.this,"You didn't initialize a case",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();//showing popup menu
    }

}
