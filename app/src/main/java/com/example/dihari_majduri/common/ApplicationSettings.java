package com.example.dihari_majduri.common;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationSettings {
    public static String ownerFirstName;
    public static String ownerLastName;
    public static String ownerMobileNumber;
    public static int ownerId;

    public static int AUTHENTICATE=1001;
    public static int NOT_AUTHENTICATE=1000;
    public static int MOBILE_NUMBER_NOT_EXISTS=1002;
    public static int MOBILE_NUMBER_EXISTS=1003;

    public static int ID_NOT_EXISTS=1004;
    public static int ID_EXISTS=1005;
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
