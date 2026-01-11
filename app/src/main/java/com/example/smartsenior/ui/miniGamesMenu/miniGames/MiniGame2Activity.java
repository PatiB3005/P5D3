package com.example.smartsenior.ui.miniGamesMenu.miniGames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MiniGame2Activity;
import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;
import com.google.android.material.button.MaterialButton;

public class MiniGame2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game2_1);

        MaterialButton next = findViewById(R.id.btnNext);

        next.setOnClickListener(v -> {
            Intent intent = new Intent(this, MiniGame22Activity.class);
            startActivity(intent);
        });
    }
}
