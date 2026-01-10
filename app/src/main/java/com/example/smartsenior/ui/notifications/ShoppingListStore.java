package com.example.smartsenior.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

class ShoppingItem {
    public String text;
    public boolean checked;

    ShoppingItem(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }
}

class ShoppingList {
    public int id;
    public String title;
    public long createdAtMillis;
    public ArrayList<ShoppingItem> items = new ArrayList<>();

    ShoppingList(int id, String title, long createdAtMillis) {
        this.id = id;
        this.title = title;
        this.createdAtMillis = createdAtMillis;
    }
}

public class ShoppingListStore {

    private static final String PREF = "shopping_lists_pref";
    private static final String KEY = "shopping_lists_json";
    private static final String KEY_NEXT_ID = "shopping_next_id";

    public static int nextId(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        int id = sp.getInt(KEY_NEXT_ID, 1);
        sp.edit().putInt(KEY_NEXT_ID, id + 1).apply();
        return id;
    }

    public static ArrayList<ShoppingList> load(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String raw = sp.getString(KEY, "[]");

        ArrayList<ShoppingList> out = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                ShoppingList sl = new ShoppingList(
                        o.getInt("id"),
                        o.optString("title", "Lista zakupów"),
                        o.optLong("createdAt", System.currentTimeMillis())
                );

                JSONArray items = o.optJSONArray("items");
                if (items != null) {
                    for (int j = 0; j < items.length(); j++) {
                        JSONObject it = items.getJSONObject(j);
                        sl.items.add(new ShoppingItem(
                                it.optString("text", ""),
                                it.optBoolean("checked", false)
                        ));
                    }
                }
                out.add(sl);
            }
        } catch (Exception ignored) {}
        return out;
    }

    public static void saveAll(Context c, ArrayList<ShoppingList> lists) {
        JSONArray arr = new JSONArray();
        try {
            for (ShoppingList sl : lists) {
                JSONObject o = new JSONObject();
                o.put("id", sl.id);
                o.put("title", sl.title);
                o.put("createdAt", sl.createdAtMillis);

                JSONArray items = new JSONArray();
                for (ShoppingItem it : sl.items) {
                    JSONObject itObj = new JSONObject();
                    itObj.put("text", it.text);
                    itObj.put("checked", it.checked);
                    items.put(itObj);
                }
                o.put("items", items);
                arr.put(o);
            }
        } catch (Exception ignored) {}

        SharedPreferences sp = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().putString(KEY, arr.toString()).apply();
    }

    public static void add(Context c, ShoppingList sl) {
        ArrayList<ShoppingList> lists = load(c);
        lists.add(sl);
        saveAll(c, lists);
    }

    public static void removeById(Context c, int id) {
        ArrayList<ShoppingList> lists = load(c);
        ArrayList<ShoppingList> out = new ArrayList<>();
        for (ShoppingList sl : lists) {
            if (sl.id != id) out.add(sl);
        }
        saveAll(c, out);
    }
    public static ShoppingList findById(Context c, int id) {
        ArrayList<ShoppingList> lists = load(c);
        for (ShoppingList sl : lists) if (sl.id == id) return sl;
        return null;
    }

    public static void update(Context c, ShoppingList updated) {
        ArrayList<ShoppingList> lists = load(c);
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).id == updated.id) {
                lists.set(i, updated);
                break;
            }
        }
        saveAll(c, lists);
    }

}
