package com.example.smartsenior.ui.trustedContacts;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrustedContactsStorage {

    private static final String PREFS = "trusted_contacts_prefs";
    private static final String KEY = "contacts_json";

    public static ArrayList<TrustedContact> load(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String raw = sp.getString(KEY, "[]");
        ArrayList<TrustedContact> out = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                TrustedContact c = TrustedContact.fromJson(o);

                String norm = PhoneUtils.normalizeToPL(c.phone);
                c.phone = (norm == null ? "" : norm);

                c.id = (c.id == null ? "" : c.id);
                c.name = (c.name == null ? "" : c.name);
                c.photoUri = (c.photoUri == null ? "" : c.photoUri);

                out.add(c);
            }
        } catch (JSONException e) {
            out.clear();
        }
        return out;
    }

    public static void save(Context ctx, ArrayList<TrustedContact> list) {
        JSONArray arr = new JSONArray();
        for (TrustedContact c : list) {
            try { arr.put(c.toJson()); } catch (JSONException ignored) {}
        }
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY, arr.toString())
                .apply();
    }
}
