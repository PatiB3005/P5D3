package com.example.smartsenior.ui.notifications;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.ensureChannel(context);

        int id = intent.getIntExtra("reminder_id", -1);
        if (id == -1) return;

        Reminder reminder = ReminderStore.findById(context, id);
        if (reminder == null) return;

        if (reminder.type == ReminderType.SHOPPING) return;


        if (Build.VERSION.SDK_INT >= 33) {
            boolean granted = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;

            if (!granted) {
                handleRepeatIfNeeded(context, reminder);
                return;
            }
        }

        String notifTitle;
        String notifText = reminder.title == null ? "" : reminder.title;

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

        // ✅ Klik w notyfikację -> NotificationsActivity + banner
        Intent open = new Intent(context, NotificationsActivity.class);
        open.putExtra("open_reminder_id", id);
        open.putExtra("open_type", reminder.type.name());

        int piFlags = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                : PendingIntent.FLAG_UPDATE_CURRENT;

        PendingIntent contentPi = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(open)
                .getPendingIntent(id, piFlags);

        // Fallback (gdyby TaskStackBuilder dał null)
        if (contentPi == null) {
            contentPi = PendingIntent.getActivity(context, id, open, piFlags);
        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle(notifTitle)
                .setContentText(notifText)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notifText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setContentIntent(contentPi);

        NotificationManagerCompat.from(context).notify(id, b.build());

        handleRepeatIfNeeded(context, reminder);
    }

    private void handleRepeatIfNeeded(Context context, Reminder reminder) {
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
    }
}
