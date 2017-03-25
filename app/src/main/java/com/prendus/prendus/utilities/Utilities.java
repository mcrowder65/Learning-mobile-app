package com.prendus.prendus.utilities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.activities.profile.ProfileActivity;
import com.prendus.prendus.activities.signup.SignupActivity;
import com.prendus.prendus.constants.Constants;
import com.prendus.prendus.constants.MenuOptions;
import com.prendus.prendus.firebase.Firebase;

import java.util.Map;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class Utilities {
    public static Firebase firebase = new Firebase();
    public static Gson g = new Gson();

    public static void populatePopup(PopupMenu popup) {
        if(isLoggedIn()) {
            popup.getMenu().add(MenuOptions.MY_QUIZZES);
            popup.getMenu().add(MenuOptions.PROFILE);
            popup.getMenu().add(MenuOptions.LOGOUT);
        } else {
            popup.getMenu().add(MenuOptions.LOGIN);
            popup.getMenu().add(MenuOptions.SIGNUP);
        }
    }
    public static void navigate(MenuItem item, Activity activity, String currentLocation) {

        if(isLoggedIn()) {
            switch((String)item.getTitle()) {
                case MenuOptions.MY_QUIZZES: {
                    if(!MenuOptions.MY_QUIZZES.equals(currentLocation)) {
                        goToActivity(MyQuizzesActivity.class, activity);
                    }
                    break;
                }
                case MenuOptions.PROFILE: {
                    if(!MenuOptions.PROFILE.equals(currentLocation)) {
                        goToActivity(ProfileActivity.class, activity);
                    }

                    break;
                }
                case MenuOptions.LOGOUT: {
                    logout();

                    goToActivity(LoginActivity.class, activity);
                    break;
                }
                default: {
                    Log.wtf(Constants.TAG, "You didn't initialize a case!");
                }
            }
        } else {
            switch((String)item.getTitle()) {
                case MenuOptions.LOGIN: {
                    if(!MenuOptions.LOGIN.equals(currentLocation)) {
                        goToActivity(LoginActivity.class, activity);
                    }
                    break;
                }

                case MenuOptions.SIGNUP: {
                    if(!MenuOptions.SIGNUP.equals(currentLocation)) {
                        goToActivity(SignupActivity.class, activity);
                    }

                    break;
                }
                default: {
                    Log.wtf(Constants.TAG, "You didn't initialize a case!");
                }
            }
        }
    }
    public static void goToActivity(Class newActivity, Activity activity) {
        Intent i = new Intent(activity.getApplicationContext(), newActivity);
        activity.startActivity(i);
    }
    public static void goToActivity(Class newActivity, Activity activity, Map<String, String> extraStrings) {
        Intent i = new Intent(activity.getApplicationContext(), newActivity);
        for(String key: extraStrings.keySet()) {
            i.putExtra(key, extraStrings.get(key));
        }
        activity.startActivity(i);
    }
    public static void showSpinner(PrendusActivity activity) {
        activity.getSpinner().setVisibility(View.VISIBLE);
    }

    public static void hideSpinner(PrendusActivity activity) {
        activity.getSpinner().setVisibility(View.GONE);
    }
    public static boolean isLoggedIn() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            return false;
        } else {
            return true;
        }
    }
    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }
    private static void logout() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }
    public static void log(String obj) {
        Log.wtf(Constants.TAG, obj);
    }
}
