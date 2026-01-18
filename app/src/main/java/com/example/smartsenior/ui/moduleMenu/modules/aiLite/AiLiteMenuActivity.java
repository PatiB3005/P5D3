package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.aiLite.quiz.AiIntroActivity;
import com.example.smartsenior.ui.moduleMenu.modules.aiLite.theory.AiTheory1Activity;
import com.google.android.material.appbar.MaterialToolbar;

public class AiLiteMenuActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_menu);

        LinearLayout aiteoria = findViewById(R.id.btnAiTeoria);
        LinearLayout aiphoto = findViewById(R.id.btnAiPhoto);

        aiteoria.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, AiTheory1Activity.class));
        });

        aiphoto.setOnClickListener(v -> {
            tts.stop();
//            startActivity(new Intent(this, AiPhoto1Activity.class));
            startActivity(new Intent(this, AiIntroActivity.class));
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
