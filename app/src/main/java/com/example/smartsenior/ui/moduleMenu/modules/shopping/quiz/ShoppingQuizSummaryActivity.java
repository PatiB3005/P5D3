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

    private ImageView finishMedal;          // summaryIcon w XML
    private TextView resultText;            // summaryTitle
    private TextView resultScore;           // summaryScore
    private TextView summaryMessage;        // summaryMessage (opis)
    private MaterialButton retryButton;     // btnRetry
    private MaterialButton backToMenuButton; // btnFinish

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

        // U Ciebie z quizu przychodzą: QUIZ_RESULT i stałe maxScore = 8
        int score = getIntent().getIntExtra("QUIZ_RESULT", 0);
        int maxScore = 8;

        setupResult(score, maxScore);

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

    private void setupResult(int score, int maxScore) {
        resultScore.setText("Twój wynik: " + score + "/" + maxScore);

        float percent = score * 100f / maxScore;
        ProfileManager pm = new ProfileManager(this);

        if (percent >= 80f) {
            // ZO1 – złoty medal
            finishMedal.setImageResource(R.drawable.ic_medal_gold);
            resultText.setText("GRATULACJE!");
            summaryMessage.setText("Świetnie rozpoznajesz fałszywe sklepy.");
            pm.upgradeMedal("ZO", "GOLD");
        } else if (percent >= 60f) {
            // ZO2 – srebrny medal
            finishMedal.setImageResource(R.drawable.ic_medal_silver);
            resultText.setText("Bardzo dobrze!");
            summaryMessage.setText("Masz dobrą intuicję, ale warto zachować czujność.");
            pm.upgradeMedal("ZO", "SILVER");
        } else if (percent >= 40f) {
            // ZO3 – brązowy medal
            finishMedal.setImageResource(R.drawable.ic_medal_bronze);
            resultText.setText("Całkiem nieźle!");
            summaryMessage.setText("Warto jeszcze poćwiczyć bezpieczne zakupy.");
            pm.upgradeMedal("ZO", "BRONZE");
        } else {
            // brak medalu – smutna buźka
            finishMedal.setImageResource(R.drawable.ic_sad_emoji);
            resultText.setText("Tym razem się nie udało.");
            summaryMessage.setText("Spróbuj jeszcze raz i uważnie analizuj sklepy.");
        }
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
