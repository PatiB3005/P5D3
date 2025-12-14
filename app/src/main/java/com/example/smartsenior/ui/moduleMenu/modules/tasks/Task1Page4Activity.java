package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.button.MaterialButton;

public class Task1Page4Activity extends AppCompatActivity {

    MaterialCardView card1, card2, card3, card4;
    MaterialButton btnFinish;
    boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1_page4);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setEnabled(false);
        btnFinish.setAlpha(0f);

        setupCard(card1, false);
        setupCard(card2, false);
        setupCard(card3, false);
        setupCard(card4, true);

        btnFinish.setOnClickListener(v -> {
            // ZALICZENIE CZĘŚCI: Task1 ukończony
            ProgressStore.markDone(this, ProgressKeys.M1_TASK1_DONE);

            startActivity(new Intent(Task1Page4Activity.this, TasksActivity.class));
        });
    }

    private void setupCard(MaterialCardView card, boolean isCorrect) {
        card.setOnClickListener(v -> {
            if (answered) return;
            answered = true;

            btnFinish.setEnabled(true);
            btnFinish.setAlpha(1f);

            disableAll();

            if (isCorrect) {
                card.setCardBackgroundColor(Color.parseColor("#A7F3D0"));
                card.setStrokeColor(Color.parseColor("#059669"));
                card.setStrokeWidth(6);
            } else {
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
                .setMessage("Prawidłowa odpowiedź to:\n\n„Nigdy nie podawaj kodów ani haseł.”")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
