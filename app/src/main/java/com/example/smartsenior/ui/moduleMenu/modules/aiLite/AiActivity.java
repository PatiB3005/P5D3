package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class AiActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_1);

        LinearLayout aiteoria = findViewById(R.id.btnAiTeoria);
        LinearLayout aiphoto = findViewById(R.id.btnAiPhoto);

        aiteoria.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, AiTheoryActivity.class));
        });

        aiphoto.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, AiPhotoActivity.class));
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
