package com.example.smartsenior.ui.help;

import android.content.Context;
import android.content.SharedPreferences;

public class HelpMessageFactory {

    public static String build(Context ctx) {
        SharedPreferences p = ctx.getSharedPreferences("profile", Context.MODE_PRIVATE);
        String name = p.getString("name", "");
        String surname = p.getString("surname", "");

        String who = (name + " " + surname).trim();
        if (who.isEmpty()) who = "Użytkownik aplikacji SmartSenior";

        return "PROŚBA O WSPARCIE\n"
                + who + " potrzebuje pomocy.\n"
                + "To wiadomość wysłana z aplikacji SmartSenior.\n"
                + "Proszę o kontakt/oddzwonienie.";
    }
}
