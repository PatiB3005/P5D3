package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class Task2Page2Activity extends AppCompatActivity {

    MaterialButton btnQ1True, btnQ1False;
    MaterialButton btnQ2True, btnQ2False;
    MaterialButton btnQ3True, btnQ3False;

    MaterialButton btnNext;

    TextView commentQ1, commentQ2, commentQ3;

    boolean answered1 = false;
    boolean answered2 = false;
    boolean answered3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2_page2);

        // ============================ POWIĄZANIA ============================

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
            // POPRAWNA? — W TYM ZADANIU FAŁSZ → prawidłowa odpowiedź to btnQ1False
            setAnswer(btnQ1True, btnQ1False, false);
            commentQ1.setText("Źle! Podejrzany link NIE jest bezpieczny.");
            commentQ1.setVisibility(View.VISIBLE);
            answered1 = true;
            checkAllAnswered();
        });

        btnQ1False.setOnClickListener(v -> {
            setAnswer(btnQ1False, btnQ1True, true);
            commentQ1.setText("Dobrze! Podejrzany link jest FAŁSZYWY.");
            commentQ1.setVisibility(View.VISIBLE);
            answered1 = true;
            checkAllAnswered();
        });


        // ===================== PYTANIE 2 =====================

        btnQ2True.setOnClickListener(v -> {
            setAnswer(btnQ2True, btnQ2False, false);
            commentQ2.setText("Źle! Wiadomości o dopłacie 1 zł to klasyczne oszustwo.");
            commentQ2.setVisibility(View.VISIBLE);
            answered2 = true;
            checkAllAnswered();
        });

        btnQ2False.setOnClickListener(v -> {
            setAnswer(btnQ2False, btnQ2True, true);
            commentQ2.setText("Dobrze! Takie wiadomości to oszustwo.");
            commentQ2.setVisibility(View.VISIBLE);
            answered2 = true;
            checkAllAnswered();
        });


        // ===================== PYTANIE 3 =====================

        btnQ3True.setOnClickListener(v -> {
            setAnswer(btnQ3True, btnQ3False, false);
            commentQ3.setText("Źle! Promocje wymagające linku to oszustwo.");
            commentQ3.setVisibility(View.VISIBLE);
            answered3 = true;
            checkAllAnswered();
        });

        btnQ3False.setOnClickListener(v -> {
            setAnswer(btnQ3False, btnQ3True, true);
            commentQ3.setText("Dobrze! Takie promocje są fałszywe.");
            commentQ3.setVisibility(View.VISIBLE);
            answered3 = true;
            checkAllAnswered();
        });


        // ===================== DALEJ =====================

        btnNext.setOnClickListener(v -> {
            startActivity(new Intent(Task2Page2Activity.this, Task2Page3Activity.class));
        });
    }


    private void setAnswer(MaterialButton selected, MaterialButton other, boolean isCorrect) {

        // Wyłączamy drugi przycisk
        other.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));

        if (isCorrect) {
            selected.setBackgroundTintList(getColorStateList(android.R.color.holo_green_light));
        } else {
            selected.setBackgroundTintList(getColorStateList(android.R.color.holo_red_light));
        }
    }


    private void checkAllAnswered() {
        if (answered1 && answered2 && answered3) {
            btnNext.setEnabled(true);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setAlpha(1f);
        }
    }
}
