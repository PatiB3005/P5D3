package com.example.smartsenior.ui.moduleMenu.modules.fakenews.theory;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class FakeNewsTheoryActivity_6 extends AppCompatActivity {

    private boolean ttsOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_theory_6);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        // WSTECZ
        btnBack.setOnClickListener(v -> finish());

        // DALEJ
        btnNext.setOnClickListener(v -> {
            startActivity(new Intent(this, FakeNewsTheoryActivity_7.class));
        });

    }
}
