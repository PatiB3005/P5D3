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

public class Task4Page2Activity extends AppCompatActivity {

    MaterialButton btnAmount, btnLink, btnSender, btnNext;
    View overlay;
    LinearLayout popupBox;
    TextView popupText;
    MaterialButton popupClose;

    boolean amountSelected = false;
    boolean senderSelected = false;
    boolean linkSelected = false;

    boolean locked = false; // blokada po błędnej lub poprawnej odpowiedzi

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

        btnAmount.setOnClickListener(v -> toggle(btnAmount, 1));
        btnSender.setOnClickListener(v -> toggle(btnSender, 2));
        btnLink.setOnClickListener(v -> toggle(btnLink, 3));

        popupClose.setOnClickListener(v -> hidePopup());
    }

    private void toggle(MaterialButton btn, int id) {

        if (locked) return; // zablokowane po wyniku

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

        // jeśli obie poprawne zaznaczone → dobry popup
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
        showNextButton(); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< DODANE
    }

    private void disableButtons() {
        btnAmount.setEnabled(false);
        btnSender.setEnabled(false);
        btnLink.setEnabled(false);
    }

    private void showNextButton() {
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setOnClickListener(v ->
                startActivity(new Intent(this, Task4Page3Activity.class))
        );
    }

    private void hidePopup() {
        overlay.setVisibility(View.GONE);
        popupBox.setVisibility(View.GONE);
    }
}
