package com.example.smartsenior.ui.moduleMenu.modules.fakenews.quiz;

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

        Button btnStart = findViewById(R.id.btnStartQuiz);

        // Start quizu
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(FakeNewsIntroActivity.this, FakeNewsQuizActivity.class);
            startActivity(intent);
        });
    }
}
