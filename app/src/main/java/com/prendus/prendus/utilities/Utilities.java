package com.prendus.prendus.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
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
}
