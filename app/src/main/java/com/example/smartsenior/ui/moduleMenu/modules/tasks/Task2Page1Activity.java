package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class Task2Page1Activity extends BaseTTSActivity {

    MaterialButton btnQ1True, btnQ1False;
    MaterialButton btnQ2True, btnQ2False;
    MaterialButton btnQ3True, btnQ3False;

    TextView commentQ1, commentQ2, commentQ3;

    MaterialButton btnNext;

    boolean answered1 = false;
    boolean answered2 = false;
    boolean answered3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2_page1);

        btnQ1True = findViewById(R.id.btnQ1True);
        btnQ1False = findViewById(R.id.btnQ1False);
        commentQ1 = findViewById(R.id.commentQ1);

        btnQ2True = findViewById(R.id.btnQ2True);
        btnQ2False = findViewById(R.id.btnQ2False);
        commentQ2 = findViewById(R.id.commentQ2);

        btnQ3True = findViewById(R.id.btnQ3True);
        btnQ3False = findViewById(R.id.btnQ3False);
        commentQ3 = findViewById(R.id.commentQ3);

        btnNext = findViewById(R.id.btnNext);

        btnQ1True.setOnClickListener(v -> {
            tts.stop();
            setAnswer(btnQ1True, btnQ1False, false);
            commentQ1.setText("Źle! Bank nigdy nie prosi o kody SMS ani hasła. To próba oszustwa.");
            commentQ1.setVisibility(View.VISIBLE);
            answered1 = true;
            checkAllAnswered();
        });

        btnQ1False.setOnClickListener(v -> {
            tts.stop();
            setAnswer(btnQ1False, btnQ1True, true);
            commentQ1.setText("Dobrze! Bank nigdy nie prosi o kody SMS ani hasła. To próba oszustwa.");
            commentQ1.setVisibility(View.VISIBLE);
            answered1 = true;
            checkAllAnswered();
        });

        btnQ2True.setOnClickListener(v -> {
            tts.stop();
            setAnswer(btnQ2True, btnQ2False, true);
            commentQ2.setText("Dobrze! Oficjalne instytucje nie wysyłają wiadomości z błędami.");
            commentQ2.setVisibility(View.VISIBLE);
            answered2 = true;
            checkAllAnswered();
        });

        btnQ2False.setOnClickListener(v -> {
            tts.stop();
            setAnswer(btnQ2False, btnQ2True, false);
            commentQ2.setText("Źle! Wiadomości z błędami często są oszustwem.");
            commentQ2.setVisibility(View.VISIBLE);
            answered2 = true;
            checkAllAnswered();
        });

        btnQ3True.setOnClickListener(v -> {
            tts.stop();
            setAnswer(btnQ3True, btnQ3False, false);
            commentQ3.setText("Źle! Kliknięcie w link może prowadzić do kradzieży danych.");
            commentQ3.setVisibility(View.VISIBLE);
            answered3 = true;
            checkAllAnswered();
        });

        btnQ3False.setOnClickListener(v -> {
            tts.stop();
            setAnswer(btnQ3False, btnQ3True, true);
            commentQ3.setText("Dobrze! Takie linki często są fałszywe.");
            commentQ3.setVisibility(View.VISIBLE);
            answered3 = true;
            checkAllAnswered();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, Task2Page2Activity.class));
        });
    }

    private void setAnswer(MaterialButton selected, MaterialButton other, boolean isCorrect) {

        selected.setClickable(false);
        other.setClickable(false);

        other.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));

        if (isCorrect) {
            selected.setBackgroundTintList(getColorStateList(android.R.color.holo_green_light));
        } else {
            selected.setBackgroundTintList(getColorStateList(android.R.color.holo_red_light));
        }
    }

    private void checkAllAnswered() {
        if (answered1 && answered2 && answered3) {
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setEnabled(true);
            btnNext.setAlpha(1f);
        }
    }

    @Override
    protected String getSpeakText() {
        // collectSpeakableTextFromLayout() może pomijać przyciski, więc dopinamy etykiety opcji:
        String base = collectSpeakableTextFromLayout();

        String o1 = btnQ1True != null && btnQ1True.getText() != null ? btnQ1True.getText().toString().trim() : "";
        String o2 = btnQ1False != null && btnQ1False.getText() != null ? btnQ1False.getText().toString().trim() : "";
        String o3 = btnQ2True != null && btnQ2True.getText() != null ? btnQ2True.getText().toString().trim() : "";
        String o4 = btnQ2False != null && btnQ2False.getText() != null ? btnQ2False.getText().toString().trim() : "";
        String o5 = btnQ3True != null && btnQ3True.getText() != null ? btnQ3True.getText().toString().trim() : "";
        String o6 = btnQ3False != null && btnQ3False.getText() != null ? btnQ3False.getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (!base.isEmpty()) sb.append(base);

        if (!o1.isEmpty() || !o2.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Pytanie pierwsze. Opcje: ").append(o1).append(", ").append(o2);
        }
        if (!o3.isEmpty() || !o4.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Pytanie drugie. Opcje: ").append(o3).append(", ").append(o4);
        }
        if (!o5.isEmpty() || !o6.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Pytanie trzecie. Opcje: ").append(o5).append(", ").append(o6);
        }

        return sb.toString().trim();
    }
}
