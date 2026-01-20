package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.smartsenior.R;
import com.example.smartsenior.data.InfoPopup;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class Task4Page4Activity extends BaseTTSActivity {

    MaterialButton btnApp, btnLink, btnContent, btnFinish, btnCheck;

    private InfoPopup infoPopup;

    boolean appSelected = false;
    boolean linkSelected = false;
    boolean contentSelected = false;

    boolean locked = false;
    boolean resultReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page4);

        infoPopup = new InfoPopup(this);

        btnApp = findViewById(R.id.btnApp);
        btnLink = findViewById(R.id.btnLink);
        btnContent = findViewById(R.id.btnContent);
        btnFinish = findViewById(R.id.btnFinish);
        btnCheck = findViewById(R.id.btnCheck);

        btnFinish.setVisibility(View.GONE);

        btnCheck.setEnabled(false);
        btnCheck.setAlpha(0.6f);

        btnApp.setOnClickListener(v -> { tts.stop(); toggle(1); });
        btnLink.setOnClickListener(v -> { tts.stop(); toggle(2); });
        btnContent.setOnClickListener(v -> { tts.stop(); toggle(3); });

        btnCheck.setOnClickListener(v -> {
            tts.stop();
            checkAnswer();
        });

        btnFinish.setOnClickListener(v -> {
            tts.stop();
            ProgressStore.markDone(this, ProgressKeys.M1_TASK4_DONE);
            Intent i = new Intent(this, TasksActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });

        infoPopup.setOnDismissListener(() -> {
            if (resultReady) showFinish();
        });
    }

    private void toggle(int id) {
        if (locked) return;

        if (id == 1) {
            appSelected = !appSelected;
            highlight(btnApp, appSelected);
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

        boolean anything = appSelected || linkSelected || contentSelected;
        btnCheck.setEnabled(anything);
        btnCheck.setAlpha(anything ? 1f : 0.6f);
    }

    private void checkAnswer() {
        if (locked) return;

        locked = true;
        resultReady = true;

        boolean correct = appSelected && linkSelected && !contentSelected;

        if (correct) infoPopup.show("Dobrze!", "");
        else infoPopup.show("Niepoprawnie.", "");

        disableButtons();
        btnCheck.setEnabled(false);
    }

    private void disableButtons() {
        btnApp.setEnabled(false);
        btnLink.setEnabled(false);
        btnContent.setEnabled(false);
    }

    private void showFinish() {
        btnFinish.setVisibility(View.VISIBLE);
        btnFinish.setEnabled(true);
        btnFinish.setAlpha(1f);
    }

    @Override
    protected String getSpeakText() {
        String base = collectSpeakableTextFromLayout();

        String a = btnApp != null && btnApp.getText() != null ? btnApp.getText().toString().trim() : "";
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
