package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class AiMedalGoldActivity extends BaseTTSActivity {

    MaterialButton btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_gold);

        // PO MEDALU => test zaliczony
        ProgressStore.markDone(this, ProgressKeys.AI_QUIZ_DONE);

        btnBackToMenu = findViewById(R.id.backToMenuButton);

        btnBackToMenu.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(AiMedalGoldActivity.this, AiActivity.class));
            finish();
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
