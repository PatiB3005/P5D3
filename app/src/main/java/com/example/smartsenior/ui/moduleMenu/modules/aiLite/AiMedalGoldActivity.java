package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.google.android.material.button.MaterialButton;

public class AiMedalGoldActivity extends AppCompatActivity {

    MaterialButton btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_gold);

        btnBackToMenu = findViewById(R.id.backToMenuButton);

        btnBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(AiMedalGoldActivity.this, AiActivity.class);
            startActivity(intent);
        });

    }

}
