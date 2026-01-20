package com.example.smartsenior.ui.moduleMenu.modules.aiLite.theory;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.aiLite.AiLiteMenuActivity;
import com.google.android.material.button.MaterialButton;

public class AiTheory5Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_theory5);

        ProgressStore.markDone(this, ProgressKeys.AI_THEORY_DONE);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(AiTheory5Activity.this, AiLiteMenuActivity.class));
            finish();
        });

        FontScaler.applyFontSize(this, findViewById(android.R.id.content));
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
