package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class Website2Activity extends AppCompatActivity {

    private MaterialButton btnFake, btnReal, btnOk, btnNext;
    private LinearLayout popupOverlay;
    private ScrollView scrollView;
    private TextView titleFalse, titleTrue;

    // Kolory bez colors.xml
    private static final int BLUE  = Color.parseColor("#2B6CB0");
    private static final int GREEN = Color.parseColor("#12B76A");
    private static final int RED   = Color.parseColor("#D92D20");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_2);

        btnFake = findViewById(R.id.btnFake);
        btnReal = findViewById(R.id.btnReal);
        btnNext = findViewById(R.id.btnNext);
        btnOk   = findViewById(R.id.btnOk);

        popupOverlay = findViewById(R.id.popupOverlay);
        titleFalse = findViewById(R.id.titleFalse);
        titleTrue  = findViewById(R.id.titleTrue);
        scrollView = findViewById(R.id.scrollView);

        // Stan początkowy: oba niebieskie
        setTint(btnFake, BLUE);
        setTint(btnReal, BLUE);
        btnFake.setAlpha(1f);
        btnReal.setAlpha(1f);

        setButtonState(btnNext, false);

        // U Ciebie: FAŁSZYWA = poprawna (true)
        btnFake.setOnClickListener(v -> handleAnswer(true));
        btnReal.setOnClickListener(v -> handleAnswer(false));

        btnNext.setOnClickListener(v ->
                startActivity(new Intent(this, Website3Activity.class))
        );

        btnOk.setOnClickListener(v -> popupOverlay.setVisibility(View.GONE));
    }

    private void handleAnswer(boolean isCorrect) {
        // blokujemy ponowne klikanie
        btnFake.setClickable(false);
        btnReal.setClickable(false);

        // odpalamy Next
        setButtonState(btnNext, true);
        btnNext.setVisibility(View.VISIBLE);

        // Kolorujemy tylko kliknięty:
        if (isCorrect) {
            // kliknięto FAŁSZYWA - dobrze
            setTint(btnFake, GREEN);
            setTint(btnReal, BLUE);
            btnFake.setAlpha(1f);
            btnReal.setAlpha(0.35f);
            ScoreManager.addPoint();
        } else {
            // kliknięto PRAWDZIWA - źle
            setTint(btnReal, RED);
            setTint(btnFake, BLUE);
            btnReal.setAlpha(1f);
            btnFake.setAlpha(0.35f);
        }

        // Popup
        popupOverlay.setVisibility(View.VISIBLE);
        if (isCorrect) {
            titleTrue.setVisibility(View.VISIBLE);
            titleFalse.setVisibility(View.GONE);
        } else {
            titleFalse.setVisibility(View.VISIBLE);
            titleTrue.setVisibility(View.GONE);
        }

        // przewijanie do Next
        scrollView.post(() -> scrollView.smoothScrollTo(0, btnNext.getBottom()));
    }

    private void setButtonState(MaterialButton button, boolean enabled) {
        button.setEnabled(enabled);
        button.setAlpha(enabled ? 1f : 0.4f);
    }

    private void setTint(MaterialButton button, int color) {
        button.setBackgroundTintList(ColorStateList.valueOf(color));
    }
}
