package com.example.smartsenior.ui.moduleMenu.modules.fakenews.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.ProfileManager;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.example.smartsenior.ui.moduleMenu.modules.fakenews.FakeNewsMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class FakeNewsResultActivity extends BaseTTSActivity {

    private ImageView finishMedal;
    private TextView resultText;
    private TextView resultScore;
    private MaterialButton retryButton;
    private MaterialButton backToMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_result);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            tts.stop();
            finish();
        });

        finishMedal = findViewById(R.id.finishMedal);
        resultText = findViewById(R.id.resultText);
        resultScore = findViewById(R.id.resultScore);
        retryButton = findViewById(R.id.retryTestButton);
        backToMenuButton = findViewById(R.id.backToMenuButton);

        int score = getIntent().getIntExtra("score", 0);
        int maxScore = getIntent().getIntExtra("maxScore", 0);
        if (maxScore == 0) maxScore = 1;

        setupResult(score, maxScore);

        retryButton.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(FakeNewsResultActivity.this, FakeNewsQuizActivity.class);
            startActivity(intent);
            finish();
        });

        backToMenuButton.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(FakeNewsResultActivity.this, FakeNewsMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupResult(int score, int maxScore) {
        resultScore.setText("Twój wynik: " + score + "/" + maxScore);
        float percent = (score * 100f) / maxScore;
        ProfileManager pm = new ProfileManager(this);

        String medalType = null;
        String message = "";

        if (percent >= 80f) {
            medalType = "GOLD";
            finishMedal.setImageResource(R.drawable.ic_medal_gold);
            message = "GRATULACJE! Świetnie rozpoznajesz fake newsy!";
        } else if (percent >= 60f) {
            medalType = "SILVER";
            finishMedal.setImageResource(R.drawable.ic_medal_silver);
            message = "Bardzo dobrze! Czasem dasz się nabra, ale jesteś czujny.";
        } else if (percent >= 40f) {
            medalType = "BRONZE";
            finishMedal.setImageResource(R.drawable.ic_medal_bronze);
            message = "Całkiem nieźle, warto jeszcze poćwiczyć rozpoznawanie fałszywych treści.";
        } else {
            finishMedal.setImageResource(R.drawable.ic_sad_emoji);
            message = "Tym razem się nie udało. Spróbuj jeszcze raz i uważaj czytaj nagłówki oraz komentarze.";
        }

        resultText.setText(message);

        if (medalType != null) {
            pm.upgradeMedal("fakenews", medalType, "Fake News Quiz", score, maxScore);
        }
    }


    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
