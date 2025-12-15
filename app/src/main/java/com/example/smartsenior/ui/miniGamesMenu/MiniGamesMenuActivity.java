package com.example.smartsenior.ui.miniGamesMenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.DictionaryActivity;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MemoryActivity;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.SafePasswordActivity;
import com.google.android.material.appbar.MaterialToolbar;


public class MiniGamesMenuActivity extends AppCompatActivity {

    private LinearLayout btnMemory, btnDictionary, btnSafePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_games_menu);

        btnMemory = findViewById(R.id.btnMemory);
        btnDictionary = findViewById(R.id.btnDictionary);
        btnSafePassword = findViewById(R.id.btnSafePassword);

        btnMemory.setOnClickListener(v -> {
            Intent intent = new Intent(MiniGamesMenuActivity.this, MemoryActivity.class);
            startActivity(intent);
        });

        btnDictionary.setOnClickListener(v -> {
            Intent intent = new Intent(MiniGamesMenuActivity.this, DictionaryActivity.class);
            startActivity(intent);
        });

        btnSafePassword.setOnClickListener(v -> {
            Intent intent = new Intent(MiniGamesMenuActivity.this, SafePasswordActivity.class);
            startActivity(intent);
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
