package com.prendus.prendus.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.MainActivity;
import com.prendus.prendus.activities.constants.Constants;
import com.prendus.prendus.utilities.Utilities;
import com.prendus.prendus.validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    private void makeSnackBar(String message) {
        Snackbar mySnackBar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), message, Snackbar.LENGTH_SHORT);
        mySnackBar.show();
    }
    public void loginClicked(View view) {
        Log.wtf(Constants.TAG, "login clicked!!!!");
        username = (EditText)findViewById(R.id.username);

        password = (EditText) findViewById(R.id.password);
        String usernameText = String.valueOf(username.getText());
        if(!Validator.isEmailValid(usernameText)) {
            makeSnackBar("email invalid!");
            return;
        }
        String passwordText = String.valueOf(password.getText());
        if(!Validator.isPasswordValid(passwordText)) {
            makeSnackBar("password invalid");
            return;
        }
        Log.wtf(Constants.TAG, "username: " + usernameText + " password: " + passwordText);
        this.logInUserWithEmailAndPassword(usernameText, passwordText);
    }
    public void movingToLogin(MenuItem item) {
        makeSnackBar("you are already on login.");
    }
    
    public void logInUserWithEmailAndPassword(String username, String password) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.wtf(Constants.TAG, "signInWithEmail", task.getException());

                }

            }
        });
    }

    public void dropDownMenuClicked(MenuItem item) {
        View menuItemView = findViewById(R.id.menu);
        PopupMenu popup = new PopupMenu(LoginActivity.this, menuItemView);
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
                            //TODO go to quizzes
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
                            Toast.makeText(LoginActivity.this,"You didn't initialize a case",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    switch((String)item.getTitle()) {
                        case "Login": {
                            Toast.makeText(LoginActivity.this,"You're already on login!",Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case "Sign up": {
                            //TODO go to signup
                            break;
                        }
                        default: {
                            Toast.makeText(LoginActivity.this,"You didn't initialize a case",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                Toast.makeText(LoginActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();//showing popup menu
    }
}
