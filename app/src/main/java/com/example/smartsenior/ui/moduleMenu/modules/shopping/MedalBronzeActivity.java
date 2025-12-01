package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.google.android.material.button.MaterialButton;

public class MedalBronzeActivity extends AppCompatActivity {

    MaterialButton btnBackToMenu, btnRetryTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_brown);

        btnBackToMenu = findViewById(R.id.backToMenuButton);
        btnRetryTest = findViewById(R.id.retryTestButton);

        btnBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MedalBronzeActivity.this, Module3Activity.class);
            startActivity(intent);
        });

        btnRetryTest.setOnClickListener(v -> {
            Intent intent = new Intent(MedalBronzeActivity.this, WebsiteActivity.class);
            startActivity(intent);
        });

    }
}
