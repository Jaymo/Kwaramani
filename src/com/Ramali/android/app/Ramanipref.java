package com.Ramali.android.app;

import android.content.Context;
import android.content.SharedPreferences;

public class Ramanipref {

    public static boolean vibrate = true;

    public static boolean flashLed = true;

    public static final int NOTIFICATION_ID = 1;

    public static final String PREFS_NAME = "RamaliService";

    private static SharedPreferences settings;

    private static SharedPreferences.Editor editor;
    
    public static int rentals = 0;

    public static void loadSettings(Context context) {
        final SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        rentals=settings.getInt("Rental", rentals);
    }

    public static void saveSettings(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putInt("Rental", rentals);
        editor.commit();
    }
}
