package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.smartsenior.R;
import com.example.smartsenior.data.InfoPopup;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class Task4Page2Activity extends BaseTTSActivity {

    MaterialButton btnAmount, btnLink, btnSender, btnNext, btnCheck;

    private InfoPopup infoPopup;

    boolean amountSelected = false;
    boolean senderSelected = false;
    boolean linkSelected = false;

    boolean locked = false;
    boolean resultReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page2);

        infoPopup = new InfoPopup(this);

        btnAmount = findViewById(R.id.btnAmount);
        btnLink = findViewById(R.id.btnLink);
        btnSender = findViewById(R.id.btnSender);
        btnNext = findViewById(R.id.btnNext);
        btnCheck = findViewById(R.id.btnCheck);

        btnNext.setVisibility(View.GONE);

        btnCheck.setEnabled(false);
        btnCheck.setAlpha(0.6f);

        btnAmount.setOnClickListener(v -> { tts.stop(); toggle(1); });
        btnSender.setOnClickListener(v -> { tts.stop(); toggle(2); });
        btnLink.setOnClickListener(v -> { tts.stop(); toggle(3); });

        btnCheck.setOnClickListener(v -> {
            tts.stop();
            checkAnswer();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Task4Page3Activity.class));
        });

        infoPopup.setOnDismissListener(() -> {
            if (resultReady) showNextButton();
        });
    }

    private void toggle(int id) {
        if (locked) return;

        if (id == 1) {
            amountSelected = !amountSelected;
            highlight(btnAmount, amountSelected);
        } else if (id == 2) {
            senderSelected = !senderSelected;
            highlight(btnSender, senderSelected);
        } else {
            linkSelected = !linkSelected;
            highlight(btnLink, linkSelected);
        }

        updateCheckButtonState();
    }

    private void highlight(MaterialButton btn, boolean selected) {
        if (selected) btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6FB6FF")));
        else btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A7D0FF")));
    }

    private void updateCheckButtonState() {
        if (locked) return;

        boolean anything = amountSelected || senderSelected || linkSelected;
        btnCheck.setEnabled(anything);
        btnCheck.setAlpha(anything ? 1f : 0.6f);
    }

    private void checkAnswer() {
        if (locked) return;

        locked = true;
        resultReady = true;

        boolean correct = senderSelected && linkSelected && !amountSelected;

        if (correct) infoPopup.show("Dobrze!", "");
        else infoPopup.show("Niepoprawnie.", "");

        disableButtons();
        btnCheck.setEnabled(false);
    }

    private void disableButtons() {
        btnAmount.setEnabled(false);
        btnSender.setEnabled(false);
        btnLink.setEnabled(false);
    }

    private void showNextButton() {
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setEnabled(true);
        btnNext.setAlpha(1f);
    }

    @Override
    protected String getSpeakText() {
        String base = collectSpeakableTextFromLayout();

        String a = btnAmount != null && btnAmount.getText() != null ? btnAmount.getText().toString().trim() : "";
        String b = btnSender != null && btnSender.getText() != null ? btnSender.getText().toString().trim() : "";
        String c = btnLink != null && btnLink.getText() != null ? btnLink.getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (!base.isEmpty()) sb.append(base);

        if (!a.isEmpty() || !b.isEmpty() || !c.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Opcje: ").append(a).append(", ").append(b).append(", ").append(c);
        }

        return sb.toString().trim();
    }
}
