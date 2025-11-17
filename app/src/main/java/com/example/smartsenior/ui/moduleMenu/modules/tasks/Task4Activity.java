package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class Task4Activity extends AppCompatActivity {

    MaterialButton btnBack, btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        btnBack = findViewById(R.id.btnBack);
        btnStart = findViewById(R.id.btnStart);

        // 🔙 WSTECZ — powrót do ekranu z zadaniami
        btnBack.setOnClickListener(v -> {
            finish(); // wraca automatycznie do poprzedniej aktywności
        });

        // ▶ ROZPOCZNIJ — przejście do pierwszej strony zadania
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(Task4Activity.this, Task4Page1Activity.class);
            startActivity(intent);
        });
    }
}
