package com.example.smartsenior.ui.moduleMenu.modules.fakenews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.fakenews.quiz.FakeNewsIntroActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class FakeNewsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_menu);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        LinearLayout btnTheory = findViewById(R.id.btnTheory);
        btnTheory.setOnClickListener(v ->
                startActivity(new Intent(this, FakeNewsTheoryActivity.class))
        );

        LinearLayout btnQuiz = findViewById(R.id.btnQuiz);
        btnQuiz.setOnClickListener(v ->
                startActivity(new Intent(this, FakeNewsIntroActivity.class))
        );
    }
}
