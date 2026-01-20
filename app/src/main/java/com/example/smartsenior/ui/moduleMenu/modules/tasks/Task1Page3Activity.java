package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.data.InfoPopup;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class Task1Page3Activity extends BaseTTSActivity {

    private MaterialCardView card1, card2, card3, card4;
    private MaterialButton btnNext;

    private InfoPopup infoPopup;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1_page3);

        infoPopup = new InfoPopup(this);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        btnNext = findViewById(R.id.btnNext);
        setNextEnabled(false);

        setupCard(card1, false);
        setupCard(card2, false);
        setupCard(card3, true);
        setupCard(card4, false);

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(Task1Page3Activity.this, Task1Page4Activity.class));
        });
    }

    private void setNextEnabled(boolean enabled) {
        btnNext.setEnabled(enabled);
        btnNext.setClickable(enabled);
        btnNext.setAlpha(enabled ? 1f : 0.4f);
    }

    private void setupCard(MaterialCardView card, boolean isCorrect) {
        card.setOnClickListener(v -> {
            tts.stop();
            if (answered) return;
            answered = true;

            disableAll();

            if (isCorrect) {
                card.setCardBackgroundColor(Color.parseColor("#A7F3D0"));
                card.setStrokeColor(Color.parseColor("#059669"));
                card.setStrokeWidth(6);

                infoPopup.setOnDismissListener(() -> setNextEnabled(true));
                infoPopup.show("Dobrze!", "Możesz przejść dalej.");
            } else {
                card.setCardBackgroundColor(Color.parseColor("#FECACA"));
                card.setStrokeColor(Color.parseColor("#DC2626"));
                card.setStrokeWidth(6);

                infoPopup.setOnDismissListener(() -> setNextEnabled(true));
                infoPopup.show(
                        "Niepoprawna odpowiedź",
                        "Prawidłowa odpowiedź to:\n\n„Nigdy nie podawaj kodów ani haseł.”"
                );
            }
        });
    }

    private void disableAll() {
        card1.setClickable(false);
        card2.setClickable(false);
        card3.setClickable(false);
        card4.setClickable(false);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
