package com.example.smartsenior.ui.moduleMenu.modules.fakenews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.google.android.material.button.MaterialButton;

public class FakeNewsResultActivity extends AppCompatActivity {

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
        int maxScore = getIntent().getIntExtra("maxScore", 10);

        setupResult(score, maxScore);

        retryButton.setOnClickListener(v -> {
            Intent intent = new Intent(FakeNewsResultActivity.this, FakeNewsQuizActivity.class);
            startActivity(intent);
            finish();
        });

        backToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(FakeNewsResultActivity.this, ModuleMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupResult(int score, int maxScore) {
        resultScore.setText("Twój wynik: " + score + " / " + maxScore);

        float percent = (score * 100f) / maxScore;

        if (percent >= 80f) {
            // ZŁOTY MEDAL
            finishMedal.setImageResource(R.drawable.medal_gold);
            resultText.setText("GRATULACJE!\nŚwietnie rozpoznajesz fake newsy!");
        } else if (percent >= 60f) {
            // SREBRNY MEDAL
            finishMedal.setImageResource(R.drawable.medal_silver);
            resultText.setText("Bardzo dobrze!\nCzasem dajesz się jeszcze nabrać, ale jesteś czujny.");
        } else if (percent >= 40f) {
            // BRĄZOWY MEDAL
            finishMedal.setImageResource(R.drawable.medal_bronze);
            resultText.setText("Całkiem nieźle,\nale warto jeszcze poćwiczyć rozpoznawanie fałszywych treści.");
        } else {
            // PORAŻKA
            finishMedal.setImageResource(R.drawable.sad_emoji);
            resultText.setText("Tym razem się nie udało.\nSpróbuj jeszcze raz i uważnie czytaj nagłówki oraz komentarze.");
        }
    }
}
