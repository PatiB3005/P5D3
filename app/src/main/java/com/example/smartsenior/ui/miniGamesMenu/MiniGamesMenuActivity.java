package com.example.smartsenior.ui.miniGamesMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MiniGame1Activity;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MiniGame2Activity;
import com.example.smartsenior.ui.miniGamesMenu.miniGames.MiniGame3Activity;


public class MiniGamesMenuActivity extends AppCompatActivity {

    private Button btnMiniGame1;
    private Button btnMiniGame2;
    private Button btnMiniGame3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_games_menu);

        btnMiniGame1 = findViewById(R.id.btnMiniGame1);
        btnMiniGame2 = findViewById(R.id.btnMiniGame2);
        btnMiniGame3 = findViewById(R.id.btnMiniGame3);

        btnMiniGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiniGamesMenuActivity.this, MiniGame1Activity.class);
                startActivity(intent);
            }
        });

        btnMiniGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiniGamesMenuActivity.this, MiniGame2Activity.class);
                startActivity(intent);
            }
        });

        btnMiniGame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiniGamesMenuActivity.this, MiniGame3Activity.class);
                startActivity(intent);
            }
        });
    }
}
