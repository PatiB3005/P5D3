package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class Task3Activity extends AppCompatActivity {

    MaterialButton btnBack, btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);

        btnBack = findViewById(R.id.btnBack);
        btnStart = findViewById(R.id.btnStart);

        // Powrót do menu zadań
        btnBack.setOnClickListener(v -> finish());

        // Start następnej strony zadania 3
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(Task3Activity.this, Task3Page1Activity.class);
            startActivity(intent);
        });
    }
}
