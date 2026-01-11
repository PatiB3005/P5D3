package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class Task4Page3Activity extends BaseTTSActivity {

    MaterialButton btnNewNumber, btnLink, btnContent, btnNext;
    View overlay;
    LinearLayout popupBox;
    TextView popupText;
    MaterialButton popupClose;

    boolean newNumberSelected = false;
    boolean linkSelected = false;
    boolean contentSelected = false;

    boolean locked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page3);

        btnNewNumber = findViewById(R.id.btnNewNumber);
        btnLink = findViewById(R.id.btnLink);
        btnContent = findViewById(R.id.btnContent);
        btnNext = findViewById(R.id.btnNext);

        overlay = findViewById(R.id.overlay);
        popupBox = findViewById(R.id.popupBox);
        popupText = findViewById(R.id.popupText);
        popupClose = findViewById(R.id.popupClose);

        btnNext.setVisibility(View.GONE);

        btnNewNumber.setOnClickListener(v -> { tts.stop(); toggle(btnNewNumber, 1); });
        btnLink.setOnClickListener(v -> { tts.stop(); toggle(btnLink, 2); });
        btnContent.setOnClickListener(v -> { tts.stop(); toggle(btnContent, 3); });

        popupClose.setOnClickListener(v -> {
            tts.stop();
            hidePopup();
        });
    }

    private void toggle(MaterialButton btn, int id) {

        if (locked) return;

        if (id == 3) {
            contentSelected = !contentSelected;
            highlight(btn, contentSelected);

            if (contentSelected) {
                showError();
                return;
            }

        } else if (id == 1) {
            newNumberSelected = !newNumberSelected;
            highlight(btn, newNumberSelected);

        } else {
            linkSelected = !linkSelected;
            highlight(btn, linkSelected);
        }

        checkIfReady();
    }

    private void highlight(MaterialButton btn, boolean selected) {
        if (selected)
            btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6FB6FF")));
        else
            btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A7D0FF")));
    }

    private void checkIfReady() {
        if (locked) return;
        if (newNumberSelected && linkSelected) {
            showSuccess();
        }
    }

    private void showSuccess() {
        locked = true;

        overlay.setVisibility(View.VISIBLE);
        popupBox.setVisibility(View.VISIBLE);

        popupText.setText("✓ Dobrze!\n");
        popupBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CFF9C7")));

        disableButtons();
        showNextButton();
    }

    private void showError() {
        locked = true;

        overlay.setVisibility(View.VISIBLE);
        popupBox.setVisibility(View.VISIBLE);

        popupText.setText("✗ Niepoprawnie.\n");
        popupBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD1D1")));

        disableButtons();
        showNextButton();
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
        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Task4Page4Activity.class));
        });
    }

    private void hidePopup() {
        overlay.setVisibility(View.GONE);
        popupBox.setVisibility(View.GONE);
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
