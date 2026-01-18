package com.example.smartsenior.ui.moduleMenu.modules.aiLite.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.data.ProfileManager;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.aiLite.AiLiteMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class AiResultActivity extends BaseTTSActivity {

    private ImageView finishMedal;
    private TextView resultText;
    private TextView resultScore;
    private MaterialButton retryButton;
    private MaterialButton backToMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_result);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(AiResultActivity.this, AiLiteMenuActivity.class));
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

        backToMenuButton.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(AiResultActivity.this, AiLiteMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        retryButton.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(AiResultActivity.this, AiIntroActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupResult(int score, int maxScore) {
        resultScore.setText("Twój wynik: " + score + "/" + maxScore);

        float percent = score * 100f / maxScore;
        ProfileManager pm = new ProfileManager(this);

        ProgressStore.markDone(this, ProgressKeys.AI_QUIZ_DONE);

        String medalType;
        String message;

        if (percent >= 80f) {
            medalType = "GOLD";
            finishMedal.setImageResource(R.drawable.ic_medal_gold);
            message = "BRAWO! Świetnie rozpoznajesz, kiedy obraz może być wygenerowany przez AI.";
        } else if (percent >= 60f) {
            medalType = "SILVER";
            finishMedal.setImageResource(R.drawable.ic_medal_silver);
            message = "Bardzo dobrze! Czasem dasz się zaskoczyć, ale zwykle trafnie oceniasz obrazy.";
        } else if (percent >= 40f) {
            medalType = "BRONZE";
            finishMedal.setImageResource(R.drawable.ic_medal_bronze);
            message = "Całkiem nieźle, warto jeszcze poćwiczyć rozpoznawanie obrazów generowanych przez AI.";
        } else {
            medalType = null;
            finishMedal.setImageResource(R.drawable.ic_sad_emoji);
            message = "Tym razem się nie udało. Spróbuj jeszcze raz i uważnie przyglądaj się szczegółom zdjęć.";
        }

        resultText.setText(message);

        // Zapisz medal z pełnymi informacjami
        if (medalType != null) {
            pm.upgradeMedal(
                    "ai_quiz",           // ID zadania (unikalny identyfikator)
                    medalType,           // GOLD, SILVER, BRONZE
                    "AI Quiz",           // Nazwa modułu (wyświetlana w profilu)
                    score,               // Osiągnięty wynik
                    maxScore             // Maksymalny możliwy wynik
            );
        }
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}