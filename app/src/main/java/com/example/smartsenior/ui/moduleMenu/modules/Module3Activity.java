package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.tasks.TasksActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.WebsiteActivity;

public class Module3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_3);

        // Znajdź kafelki po ID
        LinearLayout teoria = findViewById(R.id.btnTeoriaZO);
        LinearLayout strony = findViewById(R.id.btnWebsiteZO);
        LinearLayout zadania = findViewById(R.id.btnZadaniaZO);

        // Obsługa kliknięć — otwieranie nowych ekranów
        teoria.setOnClickListener(v -> {
            Intent intent = new Intent(this, TheoryActivity.class);
            startActivity(intent);
        });

        strony.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebsiteActivity.class);
            startActivity(intent);
        });

        zadania.setOnClickListener(v -> {
            Intent intent = new Intent(this, TasksActivity.class);
            startActivity(intent);
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

    }
}
