package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Task1Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnStart = findViewById(R.id.btnStart);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, TasksActivity.class));
        });

        btnStart.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Task1Page1Activity.class));
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
