package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class Task4Activity extends BaseTTSActivity {

    MaterialButton btnBack, btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        btnBack = findViewById(R.id.btnBack);
        btnStart = findViewById(R.id.btnStart);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnStart.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(Task4Activity.this, Task4Page1Activity.class));
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
