package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Module1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_1);

        // Znajdź kafelki po ID
        LinearLayout teoria = findViewById(R.id.btnTeoria);
        LinearLayout smsEmail = findViewById(R.id.btnSmsEmail);
        LinearLayout zadania = findViewById(R.id.btnZadania);

        // Obsługa kliknięć — otwieranie nowych ekranów
        teoria.setOnClickListener(v -> {
            Intent intent = new Intent(this, TheoryActivity.class);
            startActivity(intent);
        });

        smsEmail.setOnClickListener(v -> {
            Intent intent = new Intent(this, SmsEmailActivity.class);
            startActivity(intent);
        });

        zadania.setOnClickListener(v -> {
            Intent intent = new Intent(this, TasksActivity.class);
            startActivity(intent);
        });
    }
}
