package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.appbar.MaterialToolbar;

public class AiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_1);

        // Znajdź kafelki po ID
        LinearLayout aiteoria = findViewById(R.id.btnAiTeoria);
        LinearLayout aiphoto = findViewById(R.id.btnAiPhoto);

        // Obsługa kliknięć — otwieranie nowych ekranów
        aiteoria.setOnClickListener(v -> {
            Intent intent = new Intent(this, AiTheoryActivity.class);
            startActivity(intent);
        });

        aiphoto.setOnClickListener(v -> {
            Intent intent = new Intent(this, AiPhotoActivity.class);
            startActivity(intent);
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(AiActivity.this, ModuleMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });



    }
}
