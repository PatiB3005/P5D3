package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class AiPracticeMoreActivity extends BaseTTSActivity {

    MaterialButton btnBackToMenu, btnRetryTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_return);

        btnBackToMenu = findViewById(R.id.backToMenuButton);
        btnRetryTest = findViewById(R.id.retryTestButton);

        btnBackToMenu.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(AiPracticeMoreActivity.this, AiActivity.class));
        });

        btnRetryTest.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(AiPracticeMoreActivity.this, AiPhotoActivity.class));
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
