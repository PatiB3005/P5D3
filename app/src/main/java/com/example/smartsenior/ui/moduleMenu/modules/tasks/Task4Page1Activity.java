package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class Task4Page1Activity extends BaseTTSActivity {

    MaterialButton btnName, btnContent, btnLink, btnNext, popupClose;
    View overlay;
    LinearLayout popupBox;
    TextView popupText;

    boolean answered = false;

    private static final String GREEN = "#86EFAC";
    private static final String RED = "#FCA5A5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4_page1);

        btnName = findViewById(R.id.btnName);
        btnContent = findViewById(R.id.btnContent);
        btnLink = findViewById(R.id.btnLink);
        btnNext = findViewById(R.id.btnNext);

        overlay = findViewById(R.id.overlay);
        popupBox = findViewById(R.id.popupBox);
        popupText = findViewById(R.id.popupText);
        popupClose = findViewById(R.id.popupClose);

        btnNext.setVisibility(View.GONE);

        btnName.setOnClickListener(v -> { tts.stop(); checkAnswer(false, btnName); });
        btnContent.setOnClickListener(v -> { tts.stop(); checkAnswer(false, btnContent); });
        btnLink.setOnClickListener(v -> { tts.stop(); checkAnswer(true, btnLink); });

        popupClose.setOnClickListener(v -> {
            tts.stop();
            hidePopup();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Task4Page2Activity.class));
        });
    }

    private void checkAnswer(boolean correct, MaterialButton clicked) {

        if (answered) return;
        answered = true;

        btnNext.setVisibility(View.VISIBLE);
        btnNext.setEnabled(true);
        btnNext.setAlpha(1f);

        btnName.setClickable(false);
        btnContent.setClickable(false);
        btnLink.setClickable(false);

        if (correct) {
            clicked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GREEN)));
        } else {
            clicked.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(RED)));
        }

        showPopup(correct);
    }

    private void showPopup(boolean correct) {

        overlay.setVisibility(View.VISIBLE);
        popupBox.setVisibility(View.VISIBLE);

        if (correct) {
            popupText.setText("✓ Dobrze!");
            popupBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GREEN)));
        } else {
            popupText.setText("✗ Niepoprawnie.");
            popupBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(RED)));
        }
    }

    private void hidePopup() {
        overlay.setVisibility(View.GONE);
        popupBox.setVisibility(View.GONE);
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
