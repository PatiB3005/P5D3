package com.example.smartsenior.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileManager {

    private static final String PREF_NAME = "user_profile";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_SURNAME = "user_surname";
    private static final String KEY_AGE = "user_age";
    private static final String KEY_MEDALS = "user_medals_map";

    private SharedPreferences prefs;

    public ProfileManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveProfile(String name, String surname, String age) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_SURNAME, surname);
        editor.putString(KEY_AGE, age);
        editor.apply();
    }

    public String getName() {
        return prefs.getString(KEY_NAME, "");
    }

    public String getSurname() {
        return prefs.getString(KEY_SURNAME, "");
    }

    public String getAge() {
        return prefs.getString(KEY_AGE, "");
    }

    public boolean upgradeMedal(String taskId, String newType) {
        JSONObject map = getMedalsMapJson();

        String current = map.optString(taskId, null);

        if (current == null) {
            try {
                map.put(taskId, newType);
                saveMedalsMapJson(map);
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        if (isBetter(newType, current)) {
            try {
                map.put(taskId, newType);
                saveMedalsMapJson(map);
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public List<String> getMedalsList() {
        JSONObject map = getMedalsMapJson();
        List<String> list = new ArrayList<>();

        Iterator<String> keys = map.keys();
        while (keys.hasNext()) {
            String taskId = keys.next();
            String type = map.optString(taskId, null);
            if (type != null) {
                list.add(type);
            }
        }
        return list;
    }

    public boolean hasAnyMedals() {
        return !getMedalsList().isEmpty();
    }

    private JSONObject getMedalsMapJson() {
        String json = prefs.getString(KEY_MEDALS, null);
        if (json == null || json.isEmpty()) {
            return new JSONObject();
        }
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    private void saveMedalsMapJson(JSONObject map) {
        prefs.edit().putString(KEY_MEDALS, map.toString()).apply();
    }

    private boolean isBetter(String newType, String oldType) {
        return getRank(newType) > getRank(oldType);
    }

    private int getRank(String type) {
        if (type == null) return 0;
        switch (type) {
            case "BRONZE":
                return 1;
            case "SILVER":
                return 2;
            case "GOLD":
                return 3;
            default:
                return 0;
        }
    }
}