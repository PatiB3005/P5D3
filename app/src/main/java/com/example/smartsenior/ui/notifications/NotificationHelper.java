package com.example.smartsenior.ui.notifications;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationHelper {
    public static final String CHANNEL_ID = "reminders_channel";

    public static void ensureChannel(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;

        NotificationManager mgr = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel ch = new NotificationChannel(
                CHANNEL_ID,
                "Przypomnienia",
                NotificationManager.IMPORTANCE_HIGH
        );
        ch.setDescription("Lokalne przypomnienia o lekach, wizytach i zakupach");
        mgr.createNotificationChannel(ch);
    }
}

