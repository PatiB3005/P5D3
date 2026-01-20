package com.example.smartsenior.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReminderStore {
    private static final String PREFS = "reminders_prefs";
    private static final String KEY_JSON = "reminders_json";
    private static final String KEY_NEXT_ID = "next_id";

    public static ArrayList<Reminder> load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String raw = sp.getString(KEY_JSON, "[]");
        ArrayList<Reminder> out = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                try {
                    JSONObject o = arr.getJSONObject(i);

                    int id = o.getInt("id");
                    String typeStr = o.getString("type");

                    ReminderType type;
                    try {
                        type = ReminderType.valueOf(typeStr);
                    } catch (Exception e) {
                        continue;
                    }

                    String title = o.optString("title", "");
                    long timeMillis = o.optLong("timeMillis", 0L);
                    int repeatMinutes = o.optInt("repeatMinutes", 0);
                    String extra = o.optString("extra", "");

                    out.add(new Reminder(id, type, title, timeMillis, repeatMinutes, extra));
                } catch (Exception ignoredOneItem) {
                }
            }
        } catch (JSONException ignored) {}

        return out;
    }

    public static void save(Context context, ArrayList<Reminder> list) {
        JSONArray arr = new JSONArray();
        try {
            for (Reminder r : list) {
                JSONObject o = new JSONObject();
                o.put("id", r.id);
                o.put("type", r.type.name());
                o.put("title", r.title);
                o.put("timeMillis", r.timeMillis);
                o.put("repeatMinutes", r.repeatMinutes);
                o.put("extra", r.extra == null ? "" : r.extra);
                arr.put(o);
            }
        } catch (JSONException ignored) {}

        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_JSON, arr.toString())
                .apply();
    }

    public static int nextId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        int id = sp.getInt(KEY_NEXT_ID, 1);
        sp.edit().putInt(KEY_NEXT_ID, id + 1).apply();
        return id;
    }

    public static void add(Context context, Reminder reminder) {
        ArrayList<Reminder> list = load(context);
        list.add(reminder);
        save(context, list);
    }

    public static void removeById(Context context, int id) {
        ArrayList<Reminder> list = load(context);
        ArrayList<Reminder> out = new ArrayList<>();
        for (Reminder r : list) if (r.id != id) out.add(r);
        save(context, out);
    }

    public static Reminder findById(Context context, int id) {
        ArrayList<Reminder> list = load(context);
        for (Reminder r : list) if (r.id == id) return r;
        return null;
    }

    public static void update(Context context, Reminder updated) {
        ArrayList<Reminder> list = load(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == updated.id) {
                list.set(i, updated);
                break;
            }
        }
        save(context, list);
    }
}
