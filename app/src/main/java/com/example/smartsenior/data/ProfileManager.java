package com.example.smartsenior.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileManager {

    private static final String PREF_NAME     = "user_profile";
    private static final String KEY_NAME      = "user_name";
    private static final String KEY_PROGRESS  = "user_progress";
    private static final String KEY_MEDALS    = "user_medals_map"; // taskId -> type

    private SharedPreferences prefs;

    public ProfileManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // --------- Dane profilu (jak wcześniej) ---------

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

    // --------- Medale: mapa taskId -> medalType ---------

    /**
     * Ustawia / ulepsza medal za konkretne zadanie.
     * @param taskId np. "SMS_1"
     * @param newType "BRONZE", "SILVER" lub "GOLD"
     * @return true jeśli medal się zmienił (nowy lub ulepszony), false jeśli nic nie zrobiono.
     */
    public boolean upgradeMedal(String taskId, String newType) {
        JSONObject map = getMedalsMapJson();

        String current = map.optString(taskId, null);

        // Brak medalu – zapisujemy pierwszy.
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

        // Jest medal – sprawdź, czy nowy jest lepszy.
        if (isBetter(newType, current)) {
            try {
                map.put(taskId, newType);
                saveMedalsMapJson(map);
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Nowy nie jest lepszy – nie zmieniamy nic.
        return false;
    }

    /** Zwraca listę typów medali (bez taskId), np. ["BRONZE", "SILVER", "GOLD"]. */
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

    // --------- Pomocnicze: zapis / odczyt mapy jako JSON ---------

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

    // --------- Porównanie jakości medali ---------

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
