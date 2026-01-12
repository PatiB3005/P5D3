package com.example.smartsenior.ui.moduleMenu.modules.fakenews.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.example.smartsenior.ui.moduleMenu.modules.fakenews.FakeNewsMenuActivity;
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

        float percent = score * 100f / maxScore;

        if (percent >= 80f) {
            finishMedal.setImageResource(R.drawable.ic_medal_gold);
            resultText.setText("GRATULACJE! Świetnie rozpoznajesz fake newsy!");
        } else if (percent >= 60f) {
            finishMedal.setImageResource(R.drawable.ic_medal_silver);
            resultText.setText("Bardzo dobrze! Czasem dasz się nabrać, ale jesteś czujny.");
        } else if (percent >= 40f) {
            finishMedal.setImageResource(R.drawable.ic_medal_bronze);
            resultText.setText("Całkiem nieźle, warto jeszcze poćwiczyć rozpoznawanie fałszywych treści.");
        } else {
            finishMedal.setImageResource(R.drawable.ic_sad_emoji);
            resultText.setText("Tym razem się nie udało. Spróbuj jeszcze raz i uważnie czytaj nagłówki oraz komentarze.");
        }
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
