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

public class Task4Page4Activity extends AppCompatActivity {

    MaterialButton btnApp, btnLink, btnContent, btnFinish;
    View overlay;
    LinearLayout popupBox;
    TextView popupText;
    MaterialButton popupClose;

    boolean appSelected = false;
    boolean linkSelected = false;
    boolean contentSelected = false;

    boolean locked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page4);

        btnApp = findViewById(R.id.btnApp);
        btnLink = findViewById(R.id.btnLink);
        btnContent = findViewById(R.id.btnContent);
        btnFinish = findViewById(R.id.btnFinish);

        overlay = findViewById(R.id.overlay);
        popupBox = findViewById(R.id.popupBox);
        popupText = findViewById(R.id.popupText);
        popupClose = findViewById(R.id.popupClose);

        btnFinish.setVisibility(View.GONE);

        btnApp.setOnClickListener(v -> toggle(btnApp, 1));
        btnLink.setOnClickListener(v -> toggle(btnLink, 2));
        btnContent.setOnClickListener(v -> toggle(btnContent, 3));

        popupClose.setOnClickListener(v -> hidePopup());

        btnFinish.setOnClickListener(v -> {
            Intent i = new Intent(this, TasksActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    private void toggle(MaterialButton btn, int id) {

        if (locked) return;

        if (id == 1) {
            appSelected = !appSelected;
            highlight(btn, appSelected);

        } else if (id == 2) {
            linkSelected = !linkSelected;
            highlight(btn, linkSelected);

        } else {
            contentSelected = !contentSelected;
            highlight(btn, contentSelected);

            if (contentSelected) {
                // treść wiadomości = zła odpowiedź
                showError();
                return;
            }
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

        // poprawne: App + Link
        if (appSelected && linkSelected) {
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
        showFinish();
    }

    private void showError() {
        locked = true;

        overlay.setVisibility(View.VISIBLE);
        popupBox.setVisibility(View.VISIBLE);

        popupText.setText("✗ Niepoprawnie.\n");
        popupBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD1D1")));

        disableButtons();
        showFinish();
    }

    private void disableButtons() {
        btnApp.setEnabled(false);
        btnLink.setEnabled(false);
        btnContent.setEnabled(false);
    }

    private void showFinish() {
        btnFinish.setVisibility(View.VISIBLE);
    }

    private void hidePopup() {
        overlay.setVisibility(View.GONE);
        popupBox.setVisibility(View.GONE);
    }
}
