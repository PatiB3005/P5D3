package com.example.smartsenior.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.example.smartsenior.ui.trustedContacts.TrustedContactsActivity;

public class TripleTapHelper {

    private int tapCount = 0;
    private long lastTapTime = 0L;
    private final long windowMs;

    public TripleTapHelper(long windowMs) {
        this.windowMs = windowMs;
    }

    public boolean onTouch(Activity activity, MotionEvent ev) {
        if (ev.getAction() != MotionEvent.ACTION_UP) return false;

        long now = SystemClock.uptimeMillis();
        if (now - lastTapTime <= windowMs) tapCount++;
        else tapCount = 1;

        lastTapTime = now;

        if (tapCount >= 3) {
            tapCount = 0;

            if (!(activity instanceof TrustedContactsActivity)) {
                activity.startActivity(new Intent(activity, TrustedContactsActivity.class));
            }
            return true;
        }
        return false;
    }
}
