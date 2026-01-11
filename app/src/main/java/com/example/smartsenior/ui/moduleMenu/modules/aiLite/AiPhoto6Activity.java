package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class AiPhoto6Activity extends BaseTTSActivity {

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

        setButtonState(btnNext, false);

        // poprawna odpowiedź wg Twojej logiki:
        btnFake.setOnClickListener(v -> handleAnswer(false));
        btnReal.setOnClickListener(v -> handleAnswer(true));

        btnNext.setOnClickListener(v -> {
            tts.stop();

            int score = ScoreManageAi.score;

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

    @Override
    protected String getSpeakText() {
        String base = collectSpeakableTextFromLayout();
        String a = (btnFake != null && btnFake.getText() != null) ? btnFake.getText().toString().trim() : "";
        String b = (btnReal != null && btnReal.getText() != null) ? btnReal.getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (!base.isEmpty()) sb.append(base);

        if (!a.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Opcja pierwsza: ").append(a);
        }
        if (!b.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Opcja druga: ").append(b);
        }
        return sb.toString().trim();
    }
}
