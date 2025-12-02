package com.example.smartsenior.ui.moduleMenu.modules.fakenews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.appbar.MaterialToolbar;

public class FakeNewsIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_intro);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        Button btnStart = findViewById(R.id.btnStartQuiz);

        // Strzałka – po prostu wracamy do poprzedniego ekranu (menu modułów)
        toolbar.setNavigationOnClickListener(v -> finish());

        // Start quizu
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(FakeNewsIntroActivity.this, FakeNewsQuizActivity.class);
            startActivity(intent);
        });
    }
}
