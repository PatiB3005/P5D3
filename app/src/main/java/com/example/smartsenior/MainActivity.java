package com.example.smartsenior;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.example.smartsenior.ui.notifications.NotificationsActivity;
import com.example.smartsenior.ui.profile.ProfileActivity;
import com.example.smartsenior.ui.settings.SettingsActivity;
import com.example.smartsenior.ui.tutorial.TutorialActivity;
import com.example.smartsenior.ui.wirtualAssistant.WirtualAssistantActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnModuleMenu, btnProfile, btnMiniGames, btnWirtualAssistant, btnTutorial, btnNotifications, btnSettings, btnExit;

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

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        btnModuleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModuleMenuActivity.class);
                startActivity(intent);
            }
        });

        btnMiniGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MiniGamesMenuActivity.class);
                startActivity(intent);
            }
        });

        btnWirtualAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WirtualAssistantActivity.class);
                startActivity(intent);
            }
        });

        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(v -> {
            finish();
        });

    }
}
