package com.example.smartsenior.ui.moduleMenu.modules.shopping;

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

public class Website5Activity extends BaseTTSActivity {

    Button btnFake, btnReal, btnOk;
    MaterialButton btnNext;
    LinearLayout popupOverlay;
    ScrollView scrollView;
    TextView titleFalse, titleTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_5);

        btnFake = findViewById(R.id.btnFake);
        btnReal = findViewById(R.id.btnReal);
        btnNext = findViewById(R.id.btnNext);
        btnOk = findViewById(R.id.btnOk);
        popupOverlay = findViewById(R.id.popupOverlay);
        titleFalse = findViewById(R.id.titleFalse);
        titleTrue = findViewById(R.id.titleTrue);
        scrollView = findViewById(R.id.scrollView);

        setButtonState(btnNext, false);

        btnFake.setOnClickListener(v -> handleAnswer(true));
        btnReal.setOnClickListener(v -> handleAnswer(false));

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Website6Activity.class));
        });

        btnOk.setOnClickListener(v -> popupOverlay.setVisibility(View.GONE));
    }

    private void handleAnswer(boolean isCorrect) {
        tts.stop();

        btnFake.setClickable(false);
        btnReal.setClickable(false);
        setButtonState(btnNext, true);

        btnReal.setAlpha(isCorrect ? 0.3f : 1f);
        btnFake.setAlpha(isCorrect ? 1f : 0.3f);

        if (isCorrect) ScoreManager.addPoint();

        popupOverlay.setVisibility(View.VISIBLE);
        if (isCorrect) {
            titleTrue.setVisibility(View.VISIBLE);
            titleFalse.setVisibility(View.GONE);
        } else {
            titleFalse.setVisibility(View.VISIBLE);
            titleTrue.setVisibility(View.GONE);
        }

        btnNext.setVisibility(View.VISIBLE);
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
