package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.ShoppingTheory1Activity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz.ShoppingQuizIntroActivity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.WebsiteActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class Module3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_3);

        // Znajdź kafelki po ID
        LinearLayout teoria = findViewById(R.id.btnTeoriaZO);
        LinearLayout strony = findViewById(R.id.btnWebsiteZO);
        LinearLayout quiz = findViewById(R.id.btnQuizZO); // ← poprawiona nazwa

        // Obsługa kliknięć — otwieranie nowych ekranów
        teoria.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingTheory1Activity.class);
            startActivity(intent);
        });

        strony.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebsiteActivity.class);
            startActivity(intent);
        });

        quiz.setOnClickListener(v -> {      // ← poprawione
            Intent intent = new Intent(this, ShoppingQuizIntroActivity.class);
            startActivity(intent);
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
