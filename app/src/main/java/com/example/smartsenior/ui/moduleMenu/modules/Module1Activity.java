package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.tasks.TasksActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class Module1Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_1);

        LinearLayout teoria = findViewById(R.id.btnTeoria);
        LinearLayout smsEmail = findViewById(R.id.btnSmsEmail);
        LinearLayout zadania = findViewById(R.id.btnZadania);

        teoria.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, TheoryActivity.class));
        });

        smsEmail.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, SmsEmailActivity.class));
        });

        zadania.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, TasksActivity.class));
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            tts.stop();
            com.example.smartsenior.ui.moduleMenu.ModuleMenuNav.go(this);
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
