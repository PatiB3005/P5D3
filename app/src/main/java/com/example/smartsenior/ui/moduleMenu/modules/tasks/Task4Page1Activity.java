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

        btnNext.setVisibility(View.GONE);

        // Wszystkie kliknięcia
        btnName.setOnClickListener(v -> checkAnswer(false, btnName));
        btnContent.setOnClickListener(v -> checkAnswer(false, btnContent));
        btnLink.setOnClickListener(v -> checkAnswer(true, btnLink));

        popupClose.setOnClickListener(v -> hidePopup());

        btnNext.setOnClickListener(v ->
                startActivity(new Intent(this, Task4Page2Activity.class))
        );
    }

    private void checkAnswer(boolean correct, MaterialButton clicked) {

        if (answered) return; // blokada ponownego wyboru

        answered = true;

        // Odblokuj przycisk "Dalej"
        btnNext.setVisibility(View.VISIBLE);

        // Zablokuj wszystkie przyciski, aby nie kliknąć ponownie
        btnName.setClickable(false);
        btnContent.setClickable(false);
        btnLink.setClickable(false);

        // Kolorowanie odpowiedzi
        if (correct) {
            clicked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#86EFAC"))); // zielony
        } else {
            clicked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FCA5A5"))); // czerwony
        }

        showPopup(correct);
    }

    private void showPopup(boolean correct) {

        overlay.setVisibility(View.VISIBLE);
        popupBox.setVisibility(View.VISIBLE);

        if (correct) {
            popupText.setText("✓ Dobrze!\n");
            popupBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CFF9C7")));
        } else {
            popupText.setText("✗ Niepoprawnie.\n");
            popupBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD1D1")));
        }
    }

    private void hidePopup() {
        overlay.setVisibility(View.GONE);
        popupBox.setVisibility(View.GONE);
    }
}
