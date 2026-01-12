package com.example.smartsenior.ui.notifications;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class NotificationsActivity extends AppCompatActivity {

    private TextView btnBack;
    private MaterialButton btnAdd;

    private final ActivityResultLauncher<String> requestNotifPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        askNotificationPermissionIfNeeded();

        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> goToMainMenu());
            // jeśli wolisz tylko cofnąć do poprzedniego ekranu, zamień na:
            // btnBack.setOnClickListener(v -> finish());
        }

        if (btnAdd != null) {
            btnAdd.setOnClickListener(v -> openAddReminder());
        }

        // RecyclerViewy (rvSummary, rvCategories) zostają w XML;
        // podepniesz adaptery później (albo są w innych plikach z brancha).
    }

    private void goToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void openAddReminder() {
        // Bez zależności kompilacyjnej (jeśli AddReminderActivity jeszcze nie istnieje)
        try {
            Intent i = new Intent();
            i.setClassName(this, "com.example.smartsenior.ui.notifications.AddReminderActivity");
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Brak ekranu dodawania (AddReminderActivity)", Toast.LENGTH_SHORT).show();
        }
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
