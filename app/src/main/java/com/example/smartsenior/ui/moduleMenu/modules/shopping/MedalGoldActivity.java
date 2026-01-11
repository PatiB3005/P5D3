package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.google.android.material.button.MaterialButton;

public class MedalGoldActivity extends BaseTTSActivity {

    MaterialButton btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_gold);

        btnBackToMenu = findViewById(R.id.backToMenuButton);
        btnBackToMenu.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(MedalGoldActivity.this, Module3Activity.class));
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
