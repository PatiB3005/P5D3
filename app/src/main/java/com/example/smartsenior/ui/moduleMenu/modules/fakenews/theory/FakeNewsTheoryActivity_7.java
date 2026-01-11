package com.example.smartsenior.ui.moduleMenu.modules.fakenews.theory;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.google.android.material.button.MaterialButton;

public class FakeNewsTheoryActivity_7 extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_theory_7);

        // OSTATNI EKRAN TEORII => teoria zaliczona
        ProgressStore.markDone(this, ProgressKeys.FN_THEORY_DONE);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton backToMenuButtonFake = findViewById(R.id.backToMenuButtonFake);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        backToMenuButtonFake.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(FakeNewsTheoryActivity_7.this, ModuleMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
