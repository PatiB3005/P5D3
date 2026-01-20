package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.data.InfoPopup;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class Task4Page1Activity extends BaseTTSActivity {

    private MaterialButton btnName, btnContent, btnLink, btnNext;
    private InfoPopup infoPopup;

    private boolean answered = false;

    private static final String GREEN = "#86EFAC";
    private static final String RED = "#FCA5A5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page1);

        infoPopup = new InfoPopup(this);

        btnName = findViewById(R.id.btnName);
        btnContent = findViewById(R.id.btnContent);
        btnLink = findViewById(R.id.btnLink);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setVisibility(android.view.View.GONE);

        btnName.setOnClickListener(v -> { tts.stop(); checkAnswer(false, btnName); });
        btnContent.setOnClickListener(v -> { tts.stop(); checkAnswer(false, btnContent); });
        btnLink.setOnClickListener(v -> { tts.stop(); checkAnswer(true, btnLink); });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Task4Page2Activity.class));
        });
    }

    private void checkAnswer(boolean correct, MaterialButton clicked) {
        if (answered) return;
        answered = true;

        btnNext.setVisibility(android.view.View.VISIBLE);
        btnNext.setEnabled(true);
        btnNext.setAlpha(1f);

        btnName.setClickable(false);
        btnContent.setClickable(false);
        btnLink.setClickable(false);

        if (correct) {
            clicked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GREEN)));
            infoPopup.show("Dobrze!", "");
        } else {
            clicked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(RED)));
            infoPopup.show("Nie dobrze", "");
        }
    }

    @Override
    protected String getSpeakText() {
        String base = collectSpeakableTextFromLayout();

        String a = btnName != null && btnName.getText() != null ? btnName.getText().toString().trim() : "";
        String b = btnContent != null && btnContent.getText() != null ? btnContent.getText().toString().trim() : "";
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
