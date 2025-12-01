package com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class ShoppingQuizPage3Activity extends AppCompatActivity {

    private RadioGroup groupQ5, groupQ6;
    private TextView feedbackQ5, feedbackQ6;
    private MaterialButton btnNext;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_page3);

        groupQ5 = findViewById(R.id.groupQ5);
        groupQ6 = findViewById(R.id.groupQ6);
        feedbackQ5 = findViewById(R.id.feedbackQ5);
        feedbackQ6 = findViewById(R.id.feedbackQ6);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setEnabled(false);

        score = getIntent().getIntExtra("QUIZ_RESULT", 0);

        setupQ5();
        setupQ6();

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingQuizPage4Activity.class);
            intent.putExtra("QUIZ_RESULT", score);
            startActivity(intent);
        });
    }

    private void lockGroup(RadioGroup group) {
        for (int i = 0; i < group.getChildCount(); i++)
            group.getChildAt(i).setEnabled(false);
    }

    private void setupQ5() {
        groupQ5.setOnCheckedChangeListener((group, checkedId) -> {
            lockGroup(groupQ5);

            if (checkedId == R.id.q5_b) score++;

            feedbackQ5.setText(
                    checkedId == R.id.q5_b ?
                            "✓ Dobrze! Brak płatności przy odbiorze to sygnał ostrożności." :
                            "✗ Niepoprawnie. Literówki lub cookies nie oznaczają oszustwa."
            );

            feedbackQ5.setVisibility(View.VISIBLE);
            enableNextIfReady();
        });
    }

    private void setupQ6() {
        groupQ6.setOnCheckedChangeListener((group, checkedId) -> {
            lockGroup(groupQ6);

            if (checkedId == R.id.q6_a) score++;

            feedbackQ6.setText(
                    checkedId == R.id.q6_a ?
                            "✓ Dobrze! Rozmazane zdjęcia są najmniej podejrzane." :
                            "✗ Niepoprawnie. Idealne lub pojedyncze zdjęcie są bardziej podejrzane."
            );

            feedbackQ6.setVisibility(View.VISIBLE);
            enableNextIfReady();
        });
    }

    private void enableNextIfReady() {
        btnNext.setEnabled(
                feedbackQ5.getVisibility() == View.VISIBLE &&
                        feedbackQ6.getVisibility() == View.VISIBLE
        );
    }
}
