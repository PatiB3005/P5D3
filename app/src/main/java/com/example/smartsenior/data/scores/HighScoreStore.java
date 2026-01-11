package com.example.smartsenior.data.scores;

import android.content.Context;
import android.content.SharedPreferences;

public final class HighScoreStore {

    private static final String PREF = "scores_prefs";

    private HighScoreStore() {}

    /** Zwraca aktualny rekord (0 jeśli brak). */
    public static int getHighScore(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    /**
     * Zapisuje wynik jeśli jest większy niż rekord.
     * @return true jeśli pobito rekord
     */
    public static boolean submitHighScore(Context ctx, String key, int score) {
        if (score < 0) score = 0;

        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        int best = sp.getInt(key, 0);

        if (score > best) {
            sp.edit().putInt(key, score).apply();
            return true;
        }
        return false;
    }

    public static void resetHighScore(Context ctx, String key) {
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit().remove(key).apply();
    }
}
