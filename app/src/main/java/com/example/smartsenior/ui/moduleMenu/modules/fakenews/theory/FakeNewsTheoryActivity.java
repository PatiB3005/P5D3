package com.example.smartsenior.ui.moduleMenu.modules.fakenews.theory;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class FakeNewsTheoryActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_theory);

        // Dalej
        findViewById(R.id.btnNext).setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(FakeNewsTheoryActivity.this, FakeNewsTheoryActivity_2.class);
            startActivity(intent);
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
