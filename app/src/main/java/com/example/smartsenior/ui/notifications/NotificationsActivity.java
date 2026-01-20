package com.example.smartsenior.ui.notifications;

import android.Manifest;
import android.app.AlarmManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.smartsenior.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class NotificationsActivity extends AppCompatActivity {

    private MaterialButton btnMeds, btnVisits, btnShopping, btnOther, btnAdd;

    private View cardBanner;
    private TextView tvBannerTitle, tvBannerText;
    private MaterialButton btnBannerOpen;
    private TextView btnBannerClose;

    private final ActivityResultLauncher<String> requestNotifPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        topAppBar.setTitle("");

        NotificationHelper.ensureChannel(this);
        askNotificationPermissionIfNeeded();
        askExactAlarmPermissionIfNeeded();

        btnMeds = findViewById(R.id.btnMeds);
        btnVisits = findViewById(R.id.btnVisits);
        btnShopping = findViewById(R.id.btnShopping);
        btnOther = findViewById(R.id.btnOther);
        btnAdd = findViewById(R.id.btnAdd);

        btnMeds.setOnClickListener(v -> openCategory(ReminderType.MEDS));
        btnVisits.setOnClickListener(v -> openCategory(ReminderType.VISIT));
        btnShopping.setOnClickListener(v -> openCategory(ReminderType.SHOPPING));
        btnOther.setOnClickListener(v -> openCategory(ReminderType.OTHER));

        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddReminderActivity.class))
        );


        cardBanner = findViewById(R.id.cardBanner);
        tvBannerTitle = findViewById(R.id.tvBannerTitle);
        tvBannerText = findViewById(R.id.tvBannerText);
        btnBannerOpen = findViewById(R.id.btnBannerOpen);
        btnBannerClose = findViewById(R.id.btnBannerClose);

        handleOpenFromNotification();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleOpenFromNotification();
    }

    private void openCategory(ReminderType type) {
        Intent i = new Intent(this, CategoryItemsActivity.class);
        i.putExtra(CategoryItemsActivity.EXTRA_TYPE, type.name());
        startActivity(i);
    }

    private void handleOpenFromNotification() {
        if (cardBanner == null) return;

        int rid = getIntent().getIntExtra("open_reminder_id", -1);
        String typeStr = getIntent().getStringExtra("open_type");
        if (rid == -1 || typeStr == null) return;

        ReminderType type;
        try { type = ReminderType.valueOf(typeStr); }
        catch (Exception e) { return; }

        Reminder r = ReminderStore.findById(this, rid);
        if (r == null) return;

        cardBanner.setVisibility(View.VISIBLE);

        String title;
        switch (type) {
            case MEDS: title = "Czas na leki"; break;
            case VISIT: title = "Wizyta / badanie"; break;
            case OTHER:
            default: title = "Przypomnienie"; break;
        }

        tvBannerTitle.setText(title);
        tvBannerText.setText(r.title == null ? "" : r.title);

        btnBannerOpen.setOnClickListener(v -> {
            Intent i = new Intent(this, CategoryItemsActivity.class);
            i.putExtra(CategoryItemsActivity.EXTRA_TYPE, type.name());
            i.putExtra("highlight_reminder_id", rid);
            startActivity(i);
            cardBanner.setVisibility(View.GONE);
        });

        btnBannerClose.setOnClickListener(v -> cardBanner.setVisibility(View.GONE));

        getIntent().removeExtra("open_reminder_id");
        getIntent().removeExtra("open_type");
    }

    private void askNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33) {
            boolean granted = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;

            if (!granted) requestNotifPermission.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    private void askExactAlarmPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (am != null && !am.canScheduleExactAlarms()) {
                startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));
            }
        }
    }
}
