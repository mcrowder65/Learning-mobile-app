package com.prendus.prendus.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matt on 3/19/17.
 */

public class Validator {
    public static boolean isEmailValid(String email) {
        if(email == null) {
            return false;
        }
        email = email.replace(" ", "");

        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    public static boolean isPasswordValid(String password) {
        if(password == null) {
            return false;
        }
        password = password.replace(" ", "");
        return true;
    }
}
