package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.button.MaterialButton;

public class Task1Page1Activity extends AppCompatActivity {

    MaterialCardView card1, card2, card3, card4;
    MaterialButton btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1_page1);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setEnabled(false);
        btnNext.setAlpha(0.4f);

        setupCard(card1, true);   // poprawna
        setupCard(card2, false);
        setupCard(card3, false);
        setupCard(card4, false);

        btnNext.setOnClickListener(v ->
                startActivity(new Intent(Task1Page1Activity.this, Task1Page2Activity.class))
        );
    }

    private void setupCard(MaterialCardView card, boolean isCorrect) {
        card.setOnClickListener(v -> {

            disableAll(); // blokujemy inne karty

            // OD RAZU odblokuj przycisk Dalej
            btnNext.setEnabled(true);
            btnNext.setAlpha(1f);

            if (isCorrect) {
                // ZIELONA – poprawna
                card.setCardBackgroundColor(Color.parseColor("#A7F3D0"));
                card.setStrokeColor(Color.parseColor("#059669"));
                card.setStrokeWidth(6);
            } else {
                // CZERWONA – błędna
                card.setCardBackgroundColor(Color.parseColor("#FECACA"));
                card.setStrokeColor(Color.parseColor("#DC2626"));
                card.setStrokeWidth(6);

                showErrorDialog();
            }
        });
    }

    private void disableAll() {
        card1.setClickable(false);
        card2.setClickable(false);
        card3.setClickable(false);
        card4.setClickable(false);
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Niepoprawna odpowiedź")
                .setMessage("Prawidłowa odpowiedź to:\n\n„Nie klikaj w linki z wiadomości.”")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
