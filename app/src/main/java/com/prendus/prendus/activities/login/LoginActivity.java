package com.prendus.prendus.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.search.SearchActivity;
import com.prendus.prendus.activities.signup.SignupActivity;
import com.prendus.prendus.constants.Constants;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.constants.MenuOptions;
import com.prendus.prendus.utilities.Utilities;
import com.prendus.prendus.validators.Validator;

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
                } else {
                    //TODO decide on landing!
                }

            }
        });
    }

    public void dropDownMenuClicked(MenuItem item) {
        View menuItemView = findViewById(R.id.menu);
        PopupMenu popup = new PopupMenu(LoginActivity.this, menuItemView);
        Utilities.populatePopup(popup);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        final LoginActivity self = this;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Utilities.navigate(item, self, MenuOptions.LOGIN);
                return true;
            }
        });
        popup.show();//showing popup menu
    }
}
