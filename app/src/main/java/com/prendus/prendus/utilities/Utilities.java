package com.prendus.prendus.utilities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.activities.profile.ProfileActivity;
import com.prendus.prendus.activities.signup.SignupActivity;
import com.prendus.prendus.constants.Constants;
import com.prendus.prendus.constants.MenuOptions;
import com.prendus.prendus.firebase.Firebase;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class Utilities {
    public static Firebase firebase = new Firebase();
    public static Gson g = new Gson();
    public static boolean isLoggedIn() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void logout() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }

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
                        Intent i = new Intent(activity.getApplicationContext(), MyQuizzesActivity.class);
                        activity.startActivity(i);
                    }
                    break;
                }
                case MenuOptions.PROFILE: {
                    if(!MenuOptions.PROFILE.equals(currentLocation)) {
                        Intent i = new Intent(activity.getApplicationContext(), ProfileActivity.class);
                        activity.startActivity(i);
                    }

                    break;
                }
                case MenuOptions.LOGOUT: {
                    Utilities.logout();
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
                        Intent i = new Intent(activity.getApplicationContext(), LoginActivity.class);
                        activity.startActivity(i);
                    }
                    break;
                }

                case MenuOptions.SIGNUP: {
                    if(!MenuOptions.SIGNUP.equals(currentLocation)) {
                        Intent i = new Intent(activity.getApplicationContext(), SignupActivity.class);
                        activity.startActivity(i);
                    }

                    break;
                }
                default: {
                    Log.wtf(Constants.TAG, "You didn't initialize a case!");
                }
            }
        }
    }
}
