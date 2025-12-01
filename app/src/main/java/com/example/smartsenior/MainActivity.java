package com.example.smartsenior;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.example.smartsenior.ui.notifications.NotificationsActivity;
import com.example.smartsenior.ui.profile.ProfileActivity;
import com.example.smartsenior.ui.settings.SettingsActivity;
import com.example.smartsenior.ui.tutorial.TutorialActivity1;
import com.example.smartsenior.ui.wirtualAssistant.WirtualAssistantActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnModuleMenu, btnProfile, btnMiniGames, btnWirtualAssistant,
            btnTutorial, btnNotifications, btnSettings, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnProfile = findViewById(R.id.btnProfile);
        btnTutorial = findViewById(R.id.btnTutorial);
        btnModuleMenu = findViewById(R.id.btnModulesMenu);
        btnMiniGames = findViewById(R.id.btnMiniGames);
        btnWirtualAssistant = findViewById(R.id.btnWirtualAssistant);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnSettings = findViewById(R.id.btnSettings);
        btnExit = findViewById(R.id.btnExit);

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        btnTutorial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TutorialActivity1.class);
            startActivity(intent);
        });

        btnModuleMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ModuleMenuActivity.class);
            startActivity(intent);
        });

        btnMiniGames.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MiniGamesMenuActivity.class);
            startActivity(intent);
        });

        btnWirtualAssistant.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WirtualAssistantActivity.class);
            startActivity(intent);
        });

        btnNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        btnExit.setOnClickListener(v -> finishAffinity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyFontSize();
    }

    private void applyFontSize() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isLarge = prefs.getBoolean("large_font", false);

        float sizeNormal = 18f; // sp
        float sizeBig    = 24f; // sp
        float sizeToUse  = isLarge ? sizeBig : sizeNormal;

        setButtonTextSize(btnProfile,          sizeToUse);
        setButtonTextSize(btnTutorial,         sizeToUse);
        setButtonTextSize(btnModuleMenu,       sizeToUse);
        setButtonTextSize(btnMiniGames,        sizeToUse);
        setButtonTextSize(btnWirtualAssistant, sizeToUse);
        setButtonTextSize(btnNotifications,    sizeToUse);
        setButtonTextSize(btnSettings,         sizeToUse);
        setButtonTextSize(btnExit,             sizeToUse);
    }

    private void setButtonTextSize(Button button, float sizeSp) {
        if (button != null) {
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
        }
    }
}
