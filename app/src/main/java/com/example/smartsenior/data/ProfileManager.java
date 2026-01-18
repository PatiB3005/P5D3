package com.example.smartsenior.data;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileManager {

    public static class MedalInfo {
        public final String taskId;
        public final String type;
        public final String moduleName;
        public final int score;
        public final int maxScore;

        public MedalInfo(String taskId, String type, String moduleName, int score, int maxScore) {
            this.taskId = taskId;
            this.type = type;
            this.moduleName = moduleName;
            this.score = score;
            this.maxScore = maxScore;
        }

        public String getTitle() {
            return moduleName;
        }

        public String getDescription() {
            String medalName = getMedalName();
            String scoreText = (score == 0 && maxScore == 0) ? "" : "Wynik: " + score + "/" + maxScore + "\n";
            String medalDesc = getMedalDescription();
            return scoreText + medalName + "\n\n" + medalDesc;
        }


        private String getMedalName() {
            switch (type) {
                case "BRONZE": return "Brązowy Medal";
                case "SILVER": return "Srebrny Medal";
                case "GOLD": return "Złoty Medal";
                default: return "Medal";
            }
        }

        private String getMedalDescription() {
            switch (type) {
                case "BRONZE":
                    return "Dobry początek! Ten medal pokazuje, że podjąłeś wyzwanie i ukończyłeś moduł.";
                case "SILVER":
                    return "Świetna robota! Twój wynik pokazuje solidne opanowanie materiału.";
                case "GOLD":
                    return "Doskonale! Osiągnąłeś najwyższy poziom w tym module. Gratulacje!";
                default:
                    return "Ukończono moduł.";
            }
        }

        public JSONObject toJson() throws JSONException {
            JSONObject json = new JSONObject();
            json.put("taskId", taskId);
            json.put("type", type);
            json.put("moduleName", moduleName);
            json.put("score", score);
            json.put("maxScore", maxScore);
            return json;
        }

        public static MedalInfo fromJson(JSONObject json) throws JSONException {
            return new MedalInfo(
                    json.getString("taskId"),
                    json.getString("type"),
                    json.optString("moduleName", "Moduł"),
                    json.optInt("score", 0),
                    json.optInt("maxScore", 0)
            );
        }
    }

    private static final String PREF_NAME = "user_profile";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_SURNAME = "user_surname";
    private static final String KEY_AGE = "user_age";
    private static final String KEY_MEDALS = "user_medals_map";
    private static final String KEY_MEDALS_DETAILS = "user_medals_details";

    private SharedPreferences prefs;

    public ProfileManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveProfile(String name, String surname, String age) {
        prefs.edit()
                .putString(KEY_NAME, name)
                .putString(KEY_SURNAME, surname)
                .putString(KEY_AGE, age)
                .apply();
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

    public boolean upgradeMedal(String taskId, String newType, String moduleName, int score, int maxScore) {
        JSONObject map = getMedalsMapJson();
        String current = map.optString(taskId, null);

        boolean shouldUpdate = (current == null) || isBetter(newType, current);

        if (shouldUpdate) {
            try {
                map.put(taskId, newType);
                saveMedalsMapJson(map);
                saveMedalDetails(new MedalInfo(taskId, newType, moduleName, score, maxScore));
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public List<MedalInfo> getMedalsInfo() {
        List<MedalInfo> medals = new ArrayList<>();
        String json = prefs.getString(KEY_MEDALS_DETAILS, null);

        if (json == null || json.isEmpty()) {
            return getMedalsInfoFromOldStructure();
        }

        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                try {
                    MedalInfo medal = MedalInfo.fromJson(array.getJSONObject(i));
                    medals.add(medal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return medals;
    }

    private List<MedalInfo> getMedalsInfoFromOldStructure() {
        List<MedalInfo> medals = new ArrayList<>();
        JSONObject map = getMedalsMapJson();
        Iterator<String> keys = map.keys();

        while (keys.hasNext()) {
            String taskId = keys.next();
            String type = map.optString(taskId, null);
            if (type != null) {
                medals.add(new MedalInfo(taskId, type, "Moduł", 0, 0));
            }
        }
        return medals;
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


    private void saveMedalDetails(MedalInfo medal) throws JSONException {
        List<MedalInfo> medals = getMedalsInfo();

        // Usuń stary wpis dla tego taskId
        for (int i = medals.size() - 1; i >= 0; i--) {
            if (medals.get(i).taskId.equals(medal.taskId)) {
                medals.remove(i);
            }
        }

        medals.add(medal);

        JSONArray array = new JSONArray();
        for (MedalInfo m : medals) {
            array.put(m.toJson());
        }
        prefs.edit().putString(KEY_MEDALS_DETAILS, array.toString()).apply();
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
            case "BRONZE": return 1;
            case "SILVER": return 2;
            case "GOLD": return 3;
            default: return 0;
        }
    }

}