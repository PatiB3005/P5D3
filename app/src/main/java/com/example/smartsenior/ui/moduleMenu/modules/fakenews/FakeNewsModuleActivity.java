package com.example.smartsenior.ui.moduleMenu.modules.fakenews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.appbar.MaterialToolbar;

public class FakeNewsModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_module);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Kafelek do quizu
        LinearLayout btnQuiz = findViewById(R.id.btnFakeNewsQuiz);
        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(FakeNewsModuleActivity.this, FakeNewsIntroActivity.class);
            startActivity(intent);
        });
    }
}
