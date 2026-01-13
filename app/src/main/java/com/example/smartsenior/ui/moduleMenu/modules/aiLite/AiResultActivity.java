package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.data.ProfileManager;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
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
            // Ponowny test – wracamy do zdjęć startowych
            startActivity(new Intent(AiResultActivity.this, AiPhotoActivity.class));
            finish();
        });

        backToMenuButton.setOnClickListener(v -> {
            tts.stop();
            // Powrót do menu modułu AI Lite
            startActivity(new Intent(AiResultActivity.this, AiActivity.class));
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
