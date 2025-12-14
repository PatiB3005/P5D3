package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.google.android.material.button.MaterialButton;

public class Task2Page3Activity extends AppCompatActivity {

    MaterialButton btnQ1True, btnQ1False;
    MaterialButton btnQ2True, btnQ2False;
    MaterialButton btnQ3True, btnQ3False;
    MaterialButton btnFinish;

    TextView commentQ1, commentQ2, commentQ3;

    boolean answered1 = false;
    boolean answered2 = false;
    boolean answered3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2_page3);

        btnQ1True = findViewById(R.id.btnQ1True);
        btnQ1False = findViewById(R.id.btnQ1False);
        commentQ1 = findViewById(R.id.commentQ1);

        btnQ2True = findViewById(R.id.btnQ2True);
        btnQ2False = findViewById(R.id.btnQ2False);
        commentQ2 = findViewById(R.id.commentQ2);

        btnQ3True = findViewById(R.id.btnQ3True);
        btnQ3False = findViewById(R.id.btnQ3False);
        commentQ3 = findViewById(R.id.commentQ3);

        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setVisibility(View.GONE);
        btnFinish.setEnabled(false);
        btnFinish.setAlpha(0.4f);

        btnQ1True.setOnClickListener(v -> {
            setAnswer(btnQ1True, btnQ1False, false);
            commentQ1.setText("Źle! QR-kody mogą prowadzić do fałszywych stron lub pobierać wirusy.");
            commentQ1.setVisibility(View.VISIBLE);
            answered1 = true;
            checkAllAnswered();
        });

        btnQ1False.setOnClickListener(v -> {
            setAnswer(btnQ1False, btnQ1True, true);
            commentQ1.setText("Dobrze! QR-kody mogą prowadzić do fałszywych stron lub pobierać wirusy.");
            commentQ1.setVisibility(View.VISIBLE);
            answered1 = true;
            checkAllAnswered();
        });

        btnQ2True.setOnClickListener(v -> {
            setAnswer(btnQ2True, btnQ2False, true);
            commentQ2.setText("Dobrze! Oszust może podszyć się pod znajomego — zawsze potwierdź rozmową.");
            commentQ2.setVisibility(View.VISIBLE);
            answered2 = true;
            checkAllAnswered();
        });

        btnQ2False.setOnClickListener(v -> {
            setAnswer(btnQ2False, btnQ2True, false);
            commentQ2.setText("Źle! Oszust może podszyć się pod znajomego — zawsze potwierdź rozmową.");
            commentQ2.setVisibility(View.VISIBLE);
            answered2 = true;
            checkAllAnswered();
        });

        btnQ3True.setOnClickListener(v -> {
            setAnswer(btnQ3True, btnQ3False, true);
            commentQ3.setText("Dobrze! Słowo „PILNE” często oznacza oszustwo.");
            commentQ3.setVisibility(View.VISIBLE);
            answered3 = true;
            checkAllAnswered();
        });

        btnQ3False.setOnClickListener(v -> {
            setAnswer(btnQ3False, btnQ3True, false);
            commentQ3.setText("Źle! Słowo „PILNE” często oznacza oszustwo.");
            commentQ3.setVisibility(View.VISIBLE);
            answered3 = true;
            checkAllAnswered();
        });

        btnFinish.setOnClickListener(v -> {
            // ZALICZENIE CZĘŚCI: Task2 ukończony
            ProgressStore.markDone(this, ProgressKeys.M1_TASK2_DONE);

            Intent intent = new Intent(Task2Page3Activity.this, TasksActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setAnswer(MaterialButton selected, MaterialButton other, boolean isCorrect) {
        other.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));
        other.setTextColor(getColor(android.R.color.black));

        if (isCorrect) selected.setBackgroundTintList(getColorStateList(android.R.color.holo_green_light));
        else selected.setBackgroundTintList(getColorStateList(android.R.color.holo_red_light));

        selected.setTextColor(getColor(android.R.color.black));
    }

    private void checkAllAnswered() {
        if (answered1 && answered2 && answered3) {
            btnFinish.setVisibility(View.VISIBLE);
            btnFinish.setEnabled(true);
            btnFinish.setAlpha(1f);
        }
    }
}
