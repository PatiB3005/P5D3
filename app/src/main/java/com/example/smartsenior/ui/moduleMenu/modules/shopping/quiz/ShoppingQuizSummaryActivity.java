package com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.ProfileManager;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class ShoppingQuizSummaryActivity extends BaseTTSActivity {

    private ImageView finishMedal;
    private TextView resultText;
    private TextView resultScore;
    private TextView summaryMessage;
    private MaterialButton retryButton;
    private MaterialButton backToMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_summary);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            tts.stop();
            finish();
        });

        finishMedal = findViewById(R.id.summaryIcon);
        resultText = findViewById(R.id.summaryTitle);
        resultScore = findViewById(R.id.summaryScore);
        summaryMessage = findViewById(R.id.summaryMessage);
        retryButton = findViewById(R.id.btnRetry);
        backToMenuButton = findViewById(R.id.btnFinish);

        int score = getIntent().getIntExtra("QUIZ_RESULT", 0);
        int maxScore = 8;

        setupResult(score, maxScore, finishMedal, resultText, resultScore, summaryMessage, retryButton, backToMenuButton);

        retryButton.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(ShoppingQuizSummaryActivity.this, ShoppingQuizIntroActivity.class);
            startActivity(intent);
            finish();
        });

        backToMenuButton.setOnClickListener(v -> {
            tts.stop();
            ProgressStore.markDone(this, ProgressKeys.M3_QUIZ_DONE);
            Intent intent = new Intent(ShoppingQuizSummaryActivity.this, Module3Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupResult(int score, int maxScore, ImageView finishMedal, TextView resultText,
                             TextView resultScore, TextView summaryMessage, MaterialButton retryButton,
                             MaterialButton backToMenuButton) {

        if (resultScore != null) {
            resultScore.setText("Twój wynik: " + score + "/" + maxScore);
        }

        float percent = score * 100f / maxScore;
        ProfileManager pm = new ProfileManager(this);
        String medalType = null;
        String message = "";
        String detailedMessage = "";

        if (finishMedal != null && resultText != null && summaryMessage != null) {
            if (percent >= 80f) {
                medalType = "GOLD";
                finishMedal.setImageResource(R.drawable.ic_medal_gold);
                message = "GRATULACJE!";
                detailedMessage = "Świetnie rozpoznajesz fałszywe sklepy.";
            } else if (percent >= 60f) {
                medalType = "SILVER";
                finishMedal.setImageResource(R.drawable.ic_medal_silver);
                message = "Bardzo dobrze!";
                detailedMessage = "Masz dobrą intuicję, ale warto zachować czujność.";
            } else if (percent >= 40f) {
                medalType = "BRONZE";
                finishMedal.setImageResource(R.drawable.ic_medal_bronze);
                message = "Całkiem nieźle!";
                detailedMessage = "Warto jeszcze poćwiczyć bezpieczne zakupy.";
            } else {
                finishMedal.setImageResource(R.drawable.ic_sad_emoji);
                message = "Tym razem się nie udało.";
                detailedMessage = "Spróbuj jeszcze raz i uwaznie analizuj sklepy.";
            }
            resultText.setText(message);
            summaryMessage.setText(detailedMessage);
        }

        if (medalType != null) {
            pm.upgradeMedal("shopping", medalType, "Bezpieczne zakupy", score, maxScore);
        }

        if (retryButton != null) {
            retryButton.setOnClickListener(v -> {
                tts.stop();
                Intent intent = new Intent(ShoppingQuizSummaryActivity.this, ShoppingQuizIntroActivity.class);
                startActivity(intent);
                finish();
            });
        }

        if (backToMenuButton != null) {
            backToMenuButton.setOnClickListener(v -> {
                tts.stop();
                ProgressStore.markDone(this, ProgressKeys.M3_QUIZ_DONE);
                Intent intent = new Intent(ShoppingQuizSummaryActivity.this, Module3Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }

        getWindow().getDecorView().post(this::speakIfEnabled);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
