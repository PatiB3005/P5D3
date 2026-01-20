package com.example.smartsenior.ui.notifications;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.smartsenior.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class NotificationsActivity extends AppCompatActivity {

    private MaterialButton btnMeds, btnVisits, btnShopping, btnOther, btnAdd;

    private final ActivityResultLauncher<String> requestNotifPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());

        NotificationHelper.ensureChannel(this);
        askNotificationPermissionIfNeeded();

        btnMeds = findViewById(R.id.btnMeds);
        btnVisits = findViewById(R.id.btnVisits);
        btnShopping = findViewById(R.id.btnShopping);
        btnOther = findViewById(R.id.btnOther);
        btnAdd = findViewById(R.id.btnAdd);

        // Kafelki -> pokazują listy zapisanych danych
        btnMeds.setOnClickListener(v -> openCategory(ReminderType.MEDS));
        btnVisits.setOnClickListener(v -> openCategory(ReminderType.VISIT));
        btnShopping.setOnClickListener(v -> openCategory(ReminderType.SHOPPING));
        btnOther.setOnClickListener(v -> openCategory(ReminderType.OTHER));

        // Tylko ten przycisk dodaje nowe
        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddReminderActivity.class))
        );
    }

    private void openCategory(ReminderType type) {
        Intent i = new Intent(this, CategoryItemsActivity.class);
        i.putExtra(CategoryItemsActivity.EXTRA_TYPE, type.name());
        startActivity(i);
    }

    private void askNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33) {
            boolean granted = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;

            if (!granted) requestNotifPermission.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}
