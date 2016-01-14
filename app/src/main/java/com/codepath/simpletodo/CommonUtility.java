package com.codepath.simpletodo;

/**
 * Created by deepasaini on 1/14/16.
 */
public class CommonUtility {

    public static boolean isAlphaNumeric(String s){
        //String pattern= "^[a-zA-Z0-9 ]*$";
        String pattern = "^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }
}
