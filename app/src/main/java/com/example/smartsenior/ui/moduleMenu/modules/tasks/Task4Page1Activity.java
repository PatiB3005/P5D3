package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class Task4Page1Activity extends AppCompatActivity {

    MaterialButton btnName, btnContent, btnLink, btnNext, popupClose;
    View overlay;
    LinearLayout popupBox;
    TextView popupText;

    boolean answered = false;

    // KOLORY JAK W POPRZEDNICH ZADANIACH
    private static final String GREEN = "#86EFAC";
    private static final String RED = "#FCA5A5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page1);

        btnName = findViewById(R.id.btnName);
        btnContent = findViewById(R.id.btnContent);
        btnLink = findViewById(R.id.btnLink);
        btnNext = findViewById(R.id.btnNext);

        overlay = findViewById(R.id.overlay);
        popupBox = findViewById(R.id.popupBox);
        popupText = findViewById(R.id.popupText);
        popupClose = findViewById(R.id.popupClose);

        // NA START UKRYTY
        btnNext.setVisibility(View.GONE);

        // KLIKNIĘCIA
        btnName.setOnClickListener(v -> checkAnswer(false, btnName));
        btnContent.setOnClickListener(v -> checkAnswer(false, btnContent));
        btnLink.setOnClickListener(v -> checkAnswer(true, btnLink));

        popupClose.setOnClickListener(v -> hidePopup());

        btnNext.setOnClickListener(v ->
                startActivity(new Intent(this, Task4Page2Activity.class))
        );
    }

    private void checkAnswer(boolean correct, MaterialButton clicked) {

        if (answered) return;
        answered = true;

        // POKAŻ "DALEJ" (NIE ZMIENIAMY KOLORU – BIERZE Z XML)
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setEnabled(true);
        btnNext.setAlpha(1f);

        // BLOKADA PONOWNEGO KLIKANIA
        btnName.setClickable(false);
        btnContent.setClickable(false);
        btnLink.setClickable(false);

        // KOLOR JAK W INNYCH ZADANIACH
        if (correct) {
            clicked.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor(GREEN))
            );
        } else {
            clicked.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor(RED))
            );
        }

        showPopup(correct);
    }

    private void showPopup(boolean correct) {

        overlay.setVisibility(View.VISIBLE);
        popupBox.setVisibility(View.VISIBLE);

        if (correct) {
            popupText.setText("✓ Dobrze!");
            popupBox.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor(GREEN))
            );
        } else {
            popupText.setText("✗ Niepoprawnie.");
            popupBox.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor(RED))
            );
        }
    }

    private void hidePopup() {
        overlay.setVisibility(View.GONE);
        popupBox.setVisibility(View.GONE);
    }
}
