package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class AiPhoto6Activity extends AppCompatActivity {

    Button btnFake, btnReal, btnOk;
    MaterialButton btnNext;
    LinearLayout popupOverlay;
    ScrollView scrollView;
    TextView titleFalse, titleTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_photo6);

        btnFake = findViewById(R.id.btnFake);
        btnReal = findViewById(R.id.btnReal);
        btnNext = findViewById(R.id.btnNext);
        btnOk = findViewById(R.id.btnOk);
        popupOverlay = findViewById(R.id.popupOverlay);
        titleFalse = findViewById(R.id.titleFalse);
        titleTrue = findViewById(R.id.titleTrue);
        scrollView = findViewById(R.id.scrollView);

        // na start "Przejdź dalej" nieaktywne
        setButtonState(btnNext, false);

        // ✅ tu zostawiasz logikę odpowiedzi tak jak chcesz:
        // Jeśli poprawna ma być "AI" -> tak jak jest:
        btnFake.setOnClickListener(v -> handleAnswer(false));
        btnReal.setOnClickListener(v -> handleAnswer(true));

        // Jeśli poprawna ma być "Prawdziwe", to zamiast powyższych daj:
        // btnReal.setOnClickListener(v -> handleAnswer(true));
        // btnFake.setOnClickListener(v -> handleAnswer(false));

        // ✅ LOGIKA MEDALI po ostatnim pytaniu
        btnNext.setOnClickListener(v -> {

            // jeśli masz pole score:
            int score = ScoreManageAi.score;

            // jeśli masz metodę, to użyj zamiast tego:
            // int score = ScoreManageAi.getScore();

            if (score == 5) {
                startActivity(new Intent(AiPhoto6Activity.this, AiMedalGoldActivity.class));
            } else if (score == 4) {
                startActivity(new Intent(AiPhoto6Activity.this, AiMedalSilverActivity.class));
            } else if (score == 3) {
                startActivity(new Intent(AiPhoto6Activity.this, AiMedalBronzeActivity.class));
            } else {
                startActivity(new Intent(AiPhoto6Activity.this, AiPracticeMoreActivity.class));
            }
        });

        btnOk.setOnClickListener(v -> popupOverlay.setVisibility(View.GONE));
    }

    private void handleAnswer(boolean isCorrect) {
        btnFake.setClickable(false);
        btnReal.setClickable(false);

        setButtonState(btnNext, true);
        btnNext.setVisibility(View.VISIBLE);

        btnFake.setAlpha(isCorrect ? 1f : 0.3f);
        btnReal.setAlpha(isCorrect ? 0.3f : 1f);

        if (isCorrect) {
            ScoreManageAi.addPoint();
        }

        popupOverlay.setVisibility(View.VISIBLE);
        if (isCorrect) {
            titleTrue.setVisibility(View.VISIBLE);
            titleFalse.setVisibility(View.GONE);
        } else {
            titleFalse.setVisibility(View.VISIBLE);
            titleTrue.setVisibility(View.GONE);
        }

        scrollView.post(() -> scrollView.smoothScrollTo(0, btnNext.getBottom()));
    }

    private void setButtonState(MaterialButton button, boolean enabled) {
        button.setEnabled(enabled);
        button.setAlpha(enabled ? 1f : 0.4f);
    }
}
