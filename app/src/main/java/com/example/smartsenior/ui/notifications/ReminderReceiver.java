package com.example.smartsenior.ui.notifications;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.ensureChannel(context);

        int id = intent.getIntExtra("reminder_id", -1);
        if (id == -1) return;

        Reminder reminder = ReminderStore.findById(context, id);
        if (reminder == null) return;

        // Zakupy: brak alarmów / brak notyfikacji
        if (reminder.type == ReminderType.SHOPPING) return;

        // Android 13+: jeśli brak pozwolenia na notyfikacje, nie próbujemy notify()
        if (Build.VERSION.SDK_INT >= 33) {
            boolean granted = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;

            if (!granted) {
                // i tak ustaw ewentualne powtórzenie, ale bez wyświetlania notyfikacji
                handleRepeatIfNeeded(context, reminder);
                return;
            }
        }

        String notifTitle;
        String notifText = reminder.title;

        switch (reminder.type) {
            case MEDS:
                notifTitle = "Czas na leki";
                break;
            case VISIT:
                notifTitle = "Wizyta / badanie";
                break;
            case OTHER:
            default:
                notifTitle = "Przypomnienie";
                break;
        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle(notifTitle)
                .setContentText(notifText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat.from(context).notify(id, b.build());

        // ✅ nie usuwamy wpisu z bazy, żeby był widoczny w zakładce
        handleRepeatIfNeeded(context, reminder);
    }

    private void handleRepeatIfNeeded(Context context, Reminder reminder) {
        // Powtarzanie tylko dla leków
        if (reminder.type == ReminderType.MEDS && reminder.repeatMinutes > 0) {
            long nextTime = System.currentTimeMillis() + (reminder.repeatMinutes * 60_000L);

            Reminder updated = new Reminder(
                    reminder.id,
                    reminder.type,
                    reminder.title,
                    nextTime,
                    reminder.repeatMinutes,
                    reminder.extra
            );

            ReminderStore.update(context, updated);
            ReminderScheduler.schedule(context, updated);
        }
        // dla VISIT/OTHER nic nie robimy (zostają jako jednorazowe w bazie)
    }
}
