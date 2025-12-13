package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.WebsiteActivity;
import com.google.android.material.button.MaterialButton;

public class AiMedalSilverActivity extends AppCompatActivity {
    MaterialButton btnBackToMenu, btnRetryTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_silver);

        btnBackToMenu = findViewById(R.id.backToMenuButton);
        btnRetryTest = findViewById(R.id.retryTestButton);

        btnBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(AiMedalSilverActivity.this, AiActivity.class);
            startActivity(intent);
        });

        btnRetryTest.setOnClickListener(v -> {
            Intent intent = new Intent(AiMedalSilverActivity.this, AiPhotoActivity.class);
            startActivity(intent);
        });

    }
}
