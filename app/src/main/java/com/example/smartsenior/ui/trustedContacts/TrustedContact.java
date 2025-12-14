package com.example.smartsenior.ui.trustedContacts;

import org.json.JSONException;
import org.json.JSONObject;

public class TrustedContact {
    public String id;
    public String name;
    public String phone;
    public String photoUri; // "" gdy brak

    public JSONObject toJson() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("id", id == null ? "" : id);
        o.put("name", name == null ? "" : name);
        o.put("phone", phone == null ? "" : phone);
        o.put("photoUri", photoUri == null ? "" : photoUri);
        return o;
    }

    public static TrustedContact fromJson(JSONObject o) throws JSONException {
        TrustedContact c = new TrustedContact();
        c.id = o.optString("id", "");
        c.name = o.optString("name", "");
        c.phone = o.optString("phone", "");
        c.photoUri = o.optString("photoUri", "");
        return c;
    }
}
