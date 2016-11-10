package com.epam.hostel.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ASUS on 19.10.2016.
 */
public class Validator {

    private static final String LOGIN_REGEXP = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,40}$";
    private static final String PASSWORD_REGEXP = "[a-zA-Z0-9-_\\.]{1,45}$";

    public static boolean validateInt(int value){
        return value > 0;
    }

    public static boolean validateString(String value, int maxLength){
        if(value == null || value.isEmpty() || value.length() > maxLength)
            return false;
        else return true;
    }

    public static boolean validateLogin(String login){
        Pattern pattern = Pattern.compile(LOGIN_REGEXP);
        Matcher m = pattern.matcher(login);
        return m.matches();
    }

    public static boolean validatePassword(String password){
        Pattern pattern = Pattern.compile(PASSWORD_REGEXP);
        Matcher m = pattern.matcher(password);
        return m.matches();
    }
}
