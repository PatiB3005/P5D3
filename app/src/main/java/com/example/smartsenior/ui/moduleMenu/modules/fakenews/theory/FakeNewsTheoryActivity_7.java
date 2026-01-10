package com.example.smartsenior.ui.moduleMenu.modules.fakenews.theory;

import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.example.smartsenior.ui.moduleMenu.modules.fakenews.FakeNewsModuleActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.fakenews.FakeNewsResultActivity;
import com.google.android.material.button.MaterialButton;

public class FakeNewsTheoryActivity_7 extends AppCompatActivity {

    private boolean ttsOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_theory_7);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton backToMenuButtonFake = findViewById(R.id.backToMenuButtonFake);

        // WSTECZ
        btnBack.setOnClickListener(v -> finish());

        backToMenuButtonFake.setOnClickListener(v -> {
            Intent intent = new Intent(FakeNewsTheoryActivity_7.this, ModuleMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish(); // zamyka tylko 7
        });

    }
}
