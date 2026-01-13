package com.example.smartsenior.ui.miniGamesMenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MiniGame1Activity;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MiniGame2Activity;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MiniGame3Activity;
import com.google.android.material.appbar.MaterialToolbar;

public class MiniGamesMenuActivity extends AppCompatActivity {

    private LinearLayout btnMemory, btnDictionary, btnSafePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_games_menu);

        // Toolbar back z XML
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Kafelki z XML
        btnMemory = findViewById(R.id.btnMemory);
        btnDictionary = findViewById(R.id.btnDictionary);
        btnSafePassword = findViewById(R.id.btnSafePassword);

        // Memory -> Twoja stara gra 1
        btnMemory.setOnClickListener(v -> {
            Intent intent = new Intent(MiniGamesMenuActivity.this, MiniGame1Activity.class);
            startActivity(intent);
        });

        // Słownik -> Twoja stara gra 3
        btnDictionary.setOnClickListener(v -> {
            Intent intent = new Intent(MiniGamesMenuActivity.this, MiniGame3Activity.class);
            startActivity(intent);
        });

        // Bezpieczne hasło -> Twoja stara gra 2
        btnSafePassword.setOnClickListener(v -> {
            Intent intent = new Intent(MiniGamesMenuActivity.this, MiniGame2Activity.class);
            startActivity(intent);
        });
    }
}
