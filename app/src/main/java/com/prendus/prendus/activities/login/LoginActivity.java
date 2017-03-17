package com.prendus.prendus.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prendus.prendus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "MY APP";
    private Semaphore semaphore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
//        makeSnackBar("login init!");
        semaphore = new Semaphore(1);
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
        Log.wtf(TAG, "login clicked!!!!");
        username = (EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        String usernameText = String.valueOf(username.getText());
        String passwordText = String.valueOf(password.getText());
        Log.wtf(TAG, "username: " + usernameText + " password: " + passwordText);
        String uid = this.logInUserWithEmailAndPassword(usernameText, passwordText);
        Log.wtf(TAG, "uid: " + uid);
    }
    public void movingToLogin(MenuItem item) {
        makeSnackBar("you are already on login.");
    }
    EditText username;
    EditText password;


    public synchronized String logInUserWithEmailAndPassword(String username, String password) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final List<String> uids = new ArrayList<>();
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.wtf(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.wtf(TAG, "signInWithEmail", task.getException());

                } else {
                    String uid = auth.getCurrentUser().getUid();
                    uids.add(uid);
                    semaphore.release();
                }

            }
        });
        while(semaphore.tryAcquire()) {

        }
//        try {
//            semaphore.acquire();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return uids.size() > 0 ? uids.get(0) : null;
    }
}
