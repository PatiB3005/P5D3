package com.example.smartsenior.ui.miniGamesMenu.miniGames;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MemoryGame.MemoryActivity;
import com.google.android.material.button.MaterialButton;
import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;


public class MiniGame1Activity extends AppCompatActivity {

    private MaterialButton btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game_1);

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            Intent intent = new Intent(MiniGame1Activity.this, MiniGamesMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });


        btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MiniGame1Activity.this, MemoryActivity.class);
            startActivity(intent);
        });
    }
}
