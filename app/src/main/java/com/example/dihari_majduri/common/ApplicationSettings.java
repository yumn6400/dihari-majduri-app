package com.example.dihari_majduri.common;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationSettings {
    public static String farmerFirstName;
    public static String farmerLastName;
    public static String farmerMobileNumber;
    public static int farmerId;

    public static  void saveToSharedPreferences(Context context,String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getValueFromSharedPreferences(Context context,String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

}
