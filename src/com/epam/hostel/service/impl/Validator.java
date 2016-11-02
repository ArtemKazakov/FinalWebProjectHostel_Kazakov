package com.epam.hostel.service.impl;

/**
 * Created by ASUS on 19.10.2016.
 */
public class Validator {

    public static boolean validateId(int value){
        return value > 0;
    }

    public static boolean validateString(String value){
        if(value == null || value.isEmpty())
            return false;
        else return true;
    }
}
