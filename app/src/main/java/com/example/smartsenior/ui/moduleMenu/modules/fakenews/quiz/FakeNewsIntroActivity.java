package com.example.smartsenior.ui.moduleMenu.modules.fakenews.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class FakeNewsIntroActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_intro);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            tts.stop();
            finish();
        });

        Button btnStart = findViewById(R.id.btnStartQuiz);


        // Start quizu
        btnStart.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(FakeNewsIntroActivity.this, FakeNewsQuizActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
