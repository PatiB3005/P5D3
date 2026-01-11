package com.example.smartsenior.ui.moduleMenu;

import android.app.Activity;
import android.content.Intent;

public final class ModuleMenuNav {

    private ModuleMenuNav() {}

    public static void go(Activity activity) {
        Intent intent = new Intent(activity, ModuleMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
}
