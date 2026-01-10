package com.example.smartsenior.data.increaseFont;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontScaler {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_LARGE_FONT = "large_font";

    public static void applyFontSize(Context context, View root) {
        if (root == null) return;

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isLarge = prefs.getBoolean(KEY_LARGE_FONT, false);

        float titleSize  = isLarge ? 40f : 32f;
        float normalSize = isLarge ? 32f : 28f;
        float smallSize  = isLarge ? 26f : 22f;

        scaleTextRecursively(context, root, titleSize, normalSize, smallSize);
    }

    private static void scaleTextRecursively(Context context, View view, float titleSize, float normalSize, float smallSize) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;

            float current = tv.getTextSize() / context.getResources().getDisplayMetrics().scaledDensity;

            if (current >= 32) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
            } else if (current >= 28) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, normalSize);
            } else {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, smallSize);
            }
        }

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                scaleTextRecursively(context, group.getChildAt(i),
                        titleSize, normalSize, smallSize);
            }
        }
    }
}
