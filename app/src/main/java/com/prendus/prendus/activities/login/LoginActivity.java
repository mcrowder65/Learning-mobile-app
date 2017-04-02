package com.prendus.prendus.activities.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.constants.Constants;
import com.prendus.prendus.utilities.Utilities;
import com.prendus.prendus.validators.Validator;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class LoginActivity extends PrendusActivity {

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

        Utilities.hideSpinner(this);
    }


    public void loginClicked(View view) {
        Log.wtf(Constants.TAG, "login clicked!!!!");
        username = (EditText) findViewById(R.id.username);

        password = (EditText) findViewById(R.id.password);
        String usernameText = Utilities.stripEverything(String.valueOf(username.getText()));

        if (!Validator.isEmailValid(usernameText)) {
            makeSnackBar("email invalid!");
            return;
        }
        String passwordText = Utilities.stripEverything(String.valueOf(password.getText()));
        if (!Validator.isPasswordValid(passwordText)) {
            makeSnackBar("password invalid");
            return;
        }

        this.logInUserWithEmailAndPassword(usernameText, passwordText);

    }

    public void movingToLogin(MenuItem item) {
        makeSnackBar("you are already on login.");
    }

    public void logInUserWithEmailAndPassword(String username, String password) {
        Utilities.showSpinner(this);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final LoginActivity self = this;
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                Utilities.hideSpinner(self);
                if (!task.isSuccessful()) {
                    Log.wtf(Constants.TAG, "signInWithEmail", task.getException());
                } else {

                    if (!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                        Log.wtf(Constants.TAG, "email not verified...");
                        FirebaseAuth.getInstance().signOut();
                    } else {
                        Utilities.goToActivity(MyQuizzesActivity.class, self);
                    }

                }

            }
        });
    }

}
