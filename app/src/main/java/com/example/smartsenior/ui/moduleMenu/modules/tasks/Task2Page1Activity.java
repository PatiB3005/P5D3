package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class Task2Page1Activity extends AppCompatActivity {

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

        // ================== FIND VIEW ==================
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

        // ===================== PYTANIE 1 =====================
        btnQ1True.setOnClickListener(v -> {
            setAnswer(btnQ1True, btnQ1False, false);
            commentQ1.setText("Źle! Bank nigdy nie prosi o kody SMS ani hasła. To próba oszustwa.");
            commentQ1.setVisibility(View.VISIBLE);
            answered1 = true;
            checkAllAnswered();
        });

        btnQ1False.setOnClickListener(v -> {
            setAnswer(btnQ1False, btnQ1True, true);
            commentQ1.setText("Dobrze! Bank nigdy nie prosi o kody SMS ani hasła. To próba oszustwa.");
            commentQ1.setVisibility(View.VISIBLE);
            answered1 = true;
            checkAllAnswered();
        });

        // ===================== PYTANIE 2 =====================
        btnQ2True.setOnClickListener(v -> {
            setAnswer(btnQ2True, btnQ2False, true);
            commentQ2.setText("Dobrze! Oficjalne instytucje nie wysyłają wiadomości z błędami.");
            commentQ2.setVisibility(View.VISIBLE);
            answered2 = true;
            checkAllAnswered();
        });

        btnQ2False.setOnClickListener(v -> {
            setAnswer(btnQ2False, btnQ2True, false);
            commentQ2.setText("Źle! Wiadomości z błędami często są oszustwem.");
            commentQ2.setVisibility(View.VISIBLE);
            answered2 = true;
            checkAllAnswered();
        });

        // ===================== PYTANIE 3 =====================
        btnQ3True.setOnClickListener(v -> {
            setAnswer(btnQ3True, btnQ3False, false);
            commentQ3.setText("Źle! Kliknięcie w link może prowadzić do kradzieży danych.");
            commentQ3.setVisibility(View.VISIBLE);
            answered3 = true;
            checkAllAnswered();
        });

        btnQ3False.setOnClickListener(v -> {
            setAnswer(btnQ3False, btnQ3True, true);
            commentQ3.setText("Dobrze! Takie linki często są fałszywe.");
            commentQ3.setVisibility(View.VISIBLE);
            answered3 = true;
            checkAllAnswered();
        });

        // ===================== DALEJ =====================
        btnNext.setOnClickListener(v ->
                startActivity(new Intent(this, Task2Page2Activity.class))
        );
    }

    // =====================================================
    // IDENTYCZNA LOGIKA KOLORÓW JAK W PAGE 2
    // =====================================================
    private void setAnswer(MaterialButton selected, MaterialButton other, boolean isCorrect) {

        selected.setClickable(false);
        other.setClickable(false);

        // wyszarz drugi przycisk
        other.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));

        if (isCorrect) {
            selected.setBackgroundTintList(
                    getColorStateList(android.R.color.holo_green_light)
            );
        } else {
            selected.setBackgroundTintList(
                    getColorStateList(android.R.color.holo_red_light)
            );
        }
    }

    private void checkAllAnswered() {
        if (answered1 && answered2 && answered3) {
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setEnabled(true);
            btnNext.setAlpha(1f);
        }
    }
}
