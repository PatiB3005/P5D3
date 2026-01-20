package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.Module1Activity;
import com.google.android.material.appbar.MaterialToolbar;

public class TasksActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        LinearLayout task1 = findViewById(R.id.btnTask1);
        LinearLayout task2 = findViewById(R.id.btnTask2);
        LinearLayout task3 = findViewById(R.id.btnTask3);
        LinearLayout task4 = findViewById(R.id.btnTask4);

        task1.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(TasksActivity.this, Task1Activity.class));
        });

        task2.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(TasksActivity.this, Task2Activity.class));
        });

        task3.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(TasksActivity.this, Task3Activity.class));
        });

        task4.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(TasksActivity.this, Task4Activity.class));
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> goBackToModule());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBackToModule();
            }
        });
    }

    private void goBackToModule() {
        tts.stop();
        Intent intent = new Intent(TasksActivity.this, Module1Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
