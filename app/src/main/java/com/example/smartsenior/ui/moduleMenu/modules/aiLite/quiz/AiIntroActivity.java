package com.example.smartsenior.ui.moduleMenu.modules.aiLite.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class AiIntroActivity extends BaseTTSActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_intro);

        Button btnStart = findViewById(R.id.btnNext);
        btnStart.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(AiIntroActivity.this, AiQuizActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
