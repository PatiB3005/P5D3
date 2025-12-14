package com.example.smartsenior.data.progress;

import android.content.Context;
import android.content.SharedPreferences;

public final class ProgressStore {

    private static final String PREF = "progress_prefs";

    private ProgressStore() {}

    public static void markDone(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if (sp.getBoolean(key, false)) return; // już zaliczone
        sp.edit().putBoolean(key, true).apply();
    }

    public static boolean isDone(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static int getPercent(Context ctx, String[] partKeys) {
        if (partKeys == null || partKeys.length == 0) return 0;

        int done = 0;
        for (String k : partKeys) {
            if (isDone(ctx, k)) done++;
        }

        int pct = Math.round((done * 100f) / partKeys.length);
        if (pct < 0) pct = 0;
        if (pct > 100) pct = 100;
        return pct;
    }

    public static void resetAll(Context ctx) {
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit().clear().apply();
    }
}
