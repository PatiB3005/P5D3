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

        // Twój kod z pliku [file:5] jest OK, tylko zmień w backToMenuButton:
        backToMenuButton.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(AiResultActivity.this, AiLiteMenuActivity.class);  // Lub AiIntroActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

// W retryButton:
        retryButton.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(AiResultActivity.this, AiIntroActivity.class);  // NOWE Quiz!
            startActivity(intent);
            finish();
        });

    }

    private void setupResult(int score, int maxScore) {
        resultScore.setText("Twój wynik: " + score + "/" + maxScore);

        float percent = score * 100f / maxScore;
        ProfileManager pm = new ProfileManager(this);

        // Po wyniku cały quiz AI uznajemy za zaliczony
        ProgressStore.markDone(this, ProgressKeys.AI_QUIZ_DONE);

        if (percent >= 80f) {
            finishMedal.setImageResource(R.drawable.ic_medal_gold);
            resultText.setText("BRAWO! Świetnie rozpoznajesz, kiedy obraz może być wygenerowany przez AI.");
            pm.upgradeMedal("AI", "GOLD");
        } else if (percent >= 60f) {
            finishMedal.setImageResource(R.drawable.ic_medal_silver);
            resultText.setText("Bardzo dobrze! Czasem dasz się zaskoczyć, ale zwykle trafnie oceniasz obrazy.");
            pm.upgradeMedal("AI", "SILVER");
        } else if (percent >= 40f) {
            finishMedal.setImageResource(R.drawable.ic_medal_bronze);
            resultText.setText("Całkiem nieźle, warto jeszcze poćwiczyć rozpoznawanie obrazów generowanych przez AI.");
            pm.upgradeMedal("AI", "BRONZE");
        } else {
            finishMedal.setImageResource(R.drawable.ic_sad_emoji);
            resultText.setText("Tym razem się nie udało. Spróbuj jeszcze raz i uważnie przyglądaj się szczegółom zdjęć.");
        }
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
