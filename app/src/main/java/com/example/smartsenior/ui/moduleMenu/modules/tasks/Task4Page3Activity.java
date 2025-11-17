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

public class Task4Page3Activity extends AppCompatActivity {

    MaterialButton btnNewNumber, btnLink, btnContent, btnNext;
    View overlay;
    LinearLayout popupBox;
    TextView popupText;
    MaterialButton popupClose;

    boolean newNumberSelected = false;
    boolean linkSelected = false;
    boolean contentSelected = false;

    boolean locked = false; // blokada po błędzie lub poprawie

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

        btnNewNumber.setOnClickListener(v -> toggle(btnNewNumber, 1));
        btnLink.setOnClickListener(v -> toggle(btnLink, 2));
        btnContent.setOnClickListener(v -> toggle(btnContent, 3));

        popupClose.setOnClickListener(v -> hidePopup());
    }

    private void toggle(MaterialButton btn, int id) {

        if (locked) return; // zablokowane po wyniku

        if (id == 3) { // CONTENT = ZŁA ODPOWIEDŹ
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

        // Jeśli dwie poprawne zaznaczone → sukces
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
        btnNext.setOnClickListener(v ->
                startActivity(new Intent(this, Task4Page4Activity.class))
        );
    }

    private void hidePopup() {
        overlay.setVisibility(View.GONE);
        popupBox.setVisibility(View.GONE);
    }
}
