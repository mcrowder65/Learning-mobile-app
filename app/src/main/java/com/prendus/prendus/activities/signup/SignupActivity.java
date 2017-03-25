package com.prendus.prendus.activities.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.IPrendusActivity;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.activities.profile.ProfileActivity;
import com.prendus.prendus.activities.search.SearchActivity;
import com.prendus.prendus.activities.takequiz.TakeQuizActivity;
import com.prendus.prendus.constants.Constants;
import com.prendus.prendus.constants.MenuOptions;
import com.prendus.prendus.firebase.Firebase;
import com.prendus.prendus.objects.user.MetaData;
import com.prendus.prendus.utilities.Utilities;
import com.prendus.prendus.validators.Validator;

/**
 * Created by matt on 3/19/17.
 */

public class SignupActivity extends AppCompatActivity implements IPrendusActivity {
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private TextView firstName;
    private TextView lastName;
    private TextView school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Abstract to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Utilities.hideSpinner(this);
    }
    public void submitClicked(View view) {
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        confirmPassword = (TextView) findViewById(R.id.confirmPassword);
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        school = (TextView) findViewById(R.id.school);

        String emailStr = String.valueOf(email.getText());
        if(!Validator.isEmailValid(emailStr)) {
            Log.wtf(Constants.TAG, "email invalid");
            return;
        }
        String passwordStr = String.valueOf(password.getText());
        String confirmPasswordStr = String.valueOf(confirmPassword.getText());
        if(passwordStr == null || confirmPasswordStr == null || !passwordStr.equals(confirmPasswordStr)) {
            Log.wtf(Constants.TAG, "passwords don't match");
            return;
        }
        String firstNameStr = String.valueOf(firstName.getText());
        String lastNameStr = String.valueOf(lastName.getText());
        String schoolStr = String.valueOf(school.getText());
        this.signUpUserWithEmailAndPassword(emailStr, passwordStr, firstNameStr, lastNameStr, schoolStr);
    }
    public void signUpUserWithEmailAndPassword(final String username, String password, final String firstName, final String lastName, final String school) {
        Utilities.showSpinner(this);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final SignupActivity self = this;
        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.wtf(Constants.TAG, "signInWithEmail", task.getException());
                } else {
                    Utilities.hideSpinner(self);
                    Utilities.goToActivity(LoginActivity.class, self);
                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String path = "users/" + uid + "/metaData";
                    MetaData metaData = new MetaData(username, firstName, lastName, school, uid);
                    Utilities.firebase.update(path, metaData);
                    FirebaseAuth.getInstance().signOut();
                }

            }
        });
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
        PopupMenu popup = new PopupMenu(SignupActivity.this, menuItemView);
        Utilities.populatePopup(popup);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        final SignupActivity self = this;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Utilities.navigate(item, self, MenuOptions.SIGNUP);
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
