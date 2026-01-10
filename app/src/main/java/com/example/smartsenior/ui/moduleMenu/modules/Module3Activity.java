package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.ShoppingTheory1Activity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.WebsiteActivity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz.ShoppingQuizIntroActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class Module3Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_3);

        LinearLayout teoria = findViewById(R.id.btnTeoriaZO);
        LinearLayout strony = findViewById(R.id.btnWebsiteZO);
        LinearLayout quiz = findViewById(R.id.btnQuizZO);

        teoria.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, ShoppingTheory1Activity.class));
        });

        strony.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, WebsiteActivity.class));
        });

        quiz.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, ShoppingQuizIntroActivity.class));
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            tts.stop();
            finish();
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
