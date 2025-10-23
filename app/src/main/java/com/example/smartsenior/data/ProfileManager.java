package com.example.smartsenior.data;

import android.content.Context;
import android.content.SharedPreferences;

public class ProfileManager {

    private static final String PREF_NAME = "user_profile";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_PROGRESS = "user_progress";

    private SharedPreferences prefs;

    public ProfileManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveProfile(String name, int progress) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_NAME, name);
        editor.putInt(KEY_PROGRESS, progress);
        editor.apply();
    }

    public String getName() {
        return prefs.getString(KEY_NAME, "Brak imienia");
    }

    public int getProgress() {
        return prefs.getInt(KEY_PROGRESS, 0);
    }
}
