package com.example.dihari_majduri.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocaleHelper {
    public static void setLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);

        resources.updateConfiguration(config, resources.getDisplayMetrics());

        SharedPreferences.Editor editor = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();
    }

    public static void loadLocale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MySharedPreferences", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(context, language);
    }
}

