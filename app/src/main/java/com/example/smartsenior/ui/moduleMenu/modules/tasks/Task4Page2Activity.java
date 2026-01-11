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

public class Task4Page2Activity extends BaseTTSActivity {

    MaterialButton btnAmount, btnLink, btnSender, btnNext;
    View overlay;
    LinearLayout popupBox;
    TextView popupText;
    MaterialButton popupClose;

    boolean amountSelected = false;
    boolean senderSelected = false;
    boolean linkSelected = false;

    boolean locked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page2);

        btnAmount = findViewById(R.id.btnAmount);
        btnLink = findViewById(R.id.btnLink);
        btnSender = findViewById(R.id.btnSender);
        btnNext = findViewById(R.id.btnNext);

        overlay = findViewById(R.id.overlay);
        popupBox = findViewById(R.id.popupBox);
        popupText = findViewById(R.id.popupText);
        popupClose = findViewById(R.id.popupClose);

        btnNext.setVisibility(View.GONE);

        btnAmount.setOnClickListener(v -> { tts.stop(); toggle(btnAmount, 1); });
        btnSender.setOnClickListener(v -> { tts.stop(); toggle(btnSender, 2); });
        btnLink.setOnClickListener(v -> { tts.stop(); toggle(btnLink, 3); });

        popupClose.setOnClickListener(v -> {
            tts.stop();
            hidePopup();
        });
    }

    private void toggle(MaterialButton btn, int id) {

        if (locked) return;

        if (id == 1) {
            amountSelected = !amountSelected;
            highlight(btn, amountSelected);

            if (amountSelected) {
                showError();
                return;
            }

        } else if (id == 2) {
            senderSelected = !senderSelected;
            highlight(btn, senderSelected);

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
        if (senderSelected && linkSelected) {
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
        btnAmount.setEnabled(false);
        btnSender.setEnabled(false);
        btnLink.setEnabled(false);
    }

    private void showNextButton() {
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setEnabled(true);
        btnNext.setAlpha(1f);
        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Task4Page3Activity.class));
        });
    }

    private void hidePopup() {
        overlay.setVisibility(View.GONE);
        popupBox.setVisibility(View.GONE);
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
