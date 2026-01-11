package com.example.smartsenior.ui.miniGamesMenu.miniGames;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class MiniGame3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game3_1);

        MaterialButton next = findViewById(R.id.btnNext);

        next.setOnClickListener(v -> {
            Intent intent = new Intent(this, MiniGame32Activity.class);
            startActivity(intent);
        });
    }
}
