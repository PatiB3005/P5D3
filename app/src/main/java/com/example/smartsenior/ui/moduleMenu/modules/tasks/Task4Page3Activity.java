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

public class Task4Page3Activity extends BaseTTSActivity {

    MaterialButton btnNewNumber, btnLink, btnContent, btnNext, btnCheck;

    private InfoPopup infoPopup;

    boolean newNumberSelected = false;
    boolean linkSelected = false;
    boolean contentSelected = false;

    boolean locked = false;
    boolean resultReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page3);

        infoPopup = new InfoPopup(this);

        btnNewNumber = findViewById(R.id.btnNewNumber);
        btnLink = findViewById(R.id.btnLink);
        btnContent = findViewById(R.id.btnContent);
        btnNext = findViewById(R.id.btnNext);
        btnCheck = findViewById(R.id.btnCheck);

        btnNext.setVisibility(View.GONE);

        btnCheck.setEnabled(false);
        btnCheck.setAlpha(0.6f);

        btnNewNumber.setOnClickListener(v -> { tts.stop(); toggle(1); });
        btnLink.setOnClickListener(v -> { tts.stop(); toggle(2); });
        btnContent.setOnClickListener(v -> { tts.stop(); toggle(3); });

        btnCheck.setOnClickListener(v -> {
            tts.stop();
            checkAnswer();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Task4Page4Activity.class));
        });

        infoPopup.setOnDismissListener(() -> {
            if (resultReady) showNextButton();
        });
    }

    private void toggle(int id) {
        if (locked) return;

        if (id == 1) {
            newNumberSelected = !newNumberSelected;
            highlight(btnNewNumber, newNumberSelected);
        } else if (id == 2) {
            linkSelected = !linkSelected;
            highlight(btnLink, linkSelected);
        } else {
            contentSelected = !contentSelected;
            highlight(btnContent, contentSelected);
        }

        updateCheckButtonState();
    }

    private void highlight(MaterialButton btn, boolean selected) {
        if (selected) btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6FB6FF")));
        else btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A7D0FF")));
    }

    private void updateCheckButtonState() {
        if (locked) return;

        boolean anything = newNumberSelected || linkSelected || contentSelected;
        btnCheck.setEnabled(anything);
        btnCheck.setAlpha(anything ? 1f : 0.6f);
    }

    private void checkAnswer() {
        if (locked) return;

        locked = true;
        resultReady = true;

        boolean correct = newNumberSelected && linkSelected && !contentSelected;

        if (correct) infoPopup.show("Dobrze!", "");
        else infoPopup.show("Niepoprawnie.", "");

        disableButtons();
        btnCheck.setEnabled(false);
    }

    private void disableButtons() {
        btnNewNumber.setEnabled(false);
        btnLink.setEnabled(false);
        btnContent.setEnabled(false);
    }

    private void showNextButton() {
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setEnabled(true);
        btnNext.setAlpha(1f);
    }

    @Override
    protected String getSpeakText() {
        String base = collectSpeakableTextFromLayout();

        String a = btnNewNumber != null && btnNewNumber.getText() != null ? btnNewNumber.getText().toString().trim() : "";
        String b = btnLink != null && btnLink.getText() != null ? btnLink.getText().toString().trim() : "";
        String c = btnContent != null && btnContent.getText() != null ? btnContent.getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (!base.isEmpty()) sb.append(base);

        if (!a.isEmpty() || !b.isEmpty() || !c.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Opcje: ").append(a).append(", ").append(b).append(", ").append(c);
        }

        return sb.toString().trim();
    }
}
