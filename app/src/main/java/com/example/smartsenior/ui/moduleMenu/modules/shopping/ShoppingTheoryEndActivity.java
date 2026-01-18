package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz.ShoppingQuizIntroActivity;
import com.google.android.material.button.MaterialButton;

public class ShoppingTheoryEndActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_end);

        ProgressStore.markDone(this, ProgressKeys.M3_THEORY_DONE);

        MaterialButton btnQuiz = findViewById(R.id.btnQuiz);
        MaterialButton btnPages = findViewById(R.id.btnPages);
        MaterialButton btnExit = findViewById(R.id.btnExit);

        btnQuiz.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, ShoppingQuizIntroActivity.class));
        });

        btnPages.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, WebsiteActivity.class));
        });

        btnExit.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Module3Activity.class));
        });

        FontScaler.applyFontSize(this, findViewById(android.R.id.content));
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
