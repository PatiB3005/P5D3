package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class Website6Activity extends BaseTTSActivity {

    private Button btnFake, btnReal, btnOk;
    private MaterialButton btnNext;

    private LinearLayout popupOverlay;
    private ScrollView scrollView;
    private TextView titleFalse, titleTrue;

    private static final int BLUE  = Color.parseColor("#2B6CB0");
    private static final int GREEN = Color.parseColor("#12B76A");
    private static final int RED   = Color.parseColor("#D92D20");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_6);

        btnFake = findViewById(R.id.btnFake);
        btnReal = findViewById(R.id.btnReal);
        btnNext = findViewById(R.id.btnNext);
        btnOk   = findViewById(R.id.btnOk);

        popupOverlay = findViewById(R.id.popupOverlay);
        titleFalse = findViewById(R.id.titleFalse);
        titleTrue  = findViewById(R.id.titleTrue);
        scrollView = findViewById(R.id.scrollView);

        // Start: oba niebieskie
        setTint(btnFake, BLUE);
        setTint(btnReal, BLUE);
        btnFake.setAlpha(1f);
        btnReal.setAlpha(1f);

        setButtonState(btnNext, false);

        // U Ciebie: FAŁSZYWA = poprawna
        btnFake.setOnClickListener(v -> handleAnswer(true));
        btnReal.setOnClickListener(v -> handleAnswer(false));

        btnNext.setOnClickListener(v -> {
            tts.stop();

            // zaliczenie części: test stron ukończony
            ProgressStore.markDone(this, ProgressKeys.M3_WEBSITE_DONE);

            int score = ScoreManager.score;

            if (score == 5) {
                startActivity(new Intent(Website6Activity.this, MedalGoldActivity.class));
            } else if (score == 4) {
                startActivity(new Intent(Website6Activity.this, MedalSilverActivity.class));
            } else if (score == 3) {
                startActivity(new Intent(Website6Activity.this, MedalBronzeActivity.class));
            } else {
                startActivity(new Intent(Website6Activity.this, PracticeMoreActivity.class));
            }
        });

        btnOk.setOnClickListener(v -> popupOverlay.setVisibility(View.GONE));
    }

    private void handleAnswer(boolean isCorrect) {
        tts.stop();

        btnFake.setClickable(false);
        btnReal.setClickable(false);

        setButtonState(btnNext, true);
        btnNext.setVisibility(View.VISIBLE);

        if (isCorrect) {
            // klik FAŁSZYWA - dobrze
            setTint(btnFake, GREEN);
            setTint(btnReal, BLUE);
            btnFake.setAlpha(1f);
            btnReal.setAlpha(0.35f);
            ScoreManager.addPoint();
        } else {
            // klik PRAWDZIWA - źle
            setTint(btnReal, RED);
            setTint(btnFake, BLUE);
            btnReal.setAlpha(1f);
            btnFake.setAlpha(0.35f);
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
        if (button == null) return;
        button.setEnabled(enabled);
        button.setAlpha(enabled ? 1f : 0.4f);
    }

    private void setTint(View button, int color) {
        if (button == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setBackgroundTintList(ColorStateList.valueOf(color));
        }
    }

    @Override
    protected String getSpeakText() {
        String base = collectSpeakableTextFromLayout();
        String a = (btnFake != null && btnFake.getText() != null) ? btnFake.getText().toString().trim() : "";
        String b = (btnReal != null && btnReal.getText() != null) ? btnReal.getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (base != null && !base.trim().isEmpty()) sb.append(base.trim());

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
