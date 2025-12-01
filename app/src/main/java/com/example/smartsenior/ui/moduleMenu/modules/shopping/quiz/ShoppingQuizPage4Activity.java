package com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class ShoppingQuizPage4Activity extends AppCompatActivity {

    private RadioGroup groupQ1, groupQ2;
    private TextView feedbackQ1, feedbackQ2;
    private MaterialButton btnNext;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_page4);

        groupQ1 = findViewById(R.id.groupQ1);
        groupQ2 = findViewById(R.id.groupQ2);
        feedbackQ1 = findViewById(R.id.feedbackQ1);
        feedbackQ2 = findViewById(R.id.feedbackQ2);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setEnabled(false);

        score = getIntent().getIntExtra("QUIZ_RESULT", 0);

        setupQ1();
        setupQ2();

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingQuizSummaryActivity.class);
            intent.putExtra("QUIZ_RESULT", score);
            startActivity(intent);
        });
    }

    private void lockGroup(RadioGroup group) {
        for (int i = 0; i < group.getChildCount(); i++)
            group.getChildAt(i).setEnabled(false);
    }

    private void setupQ1() {
        groupQ1.setOnCheckedChangeListener((group, checkedId) -> {
            lockGroup(groupQ1);

            if (checkedId == R.id.q1_b) score++;

            feedbackQ1.setText(
                    checkedId == R.id.q1_b ?
                            "✓ Dobrze! Identyczne opinie w krótkim czasie to znak fałszu." :
                            "✗ Niepoprawnie. Najgroźniejsze są opinie pisane w tym samym stylu."
            );

            feedbackQ1.setVisibility(View.VISIBLE);
            enableNextIfReady();
        });
    }

    private void setupQ2() {
        groupQ2.setOnCheckedChangeListener((group, checkedId) -> {
            lockGroup(groupQ2);

            if (checkedId == R.id.q2_a) score++;

            feedbackQ2.setText(
                    checkedId == R.id.q2_a ?
                            "✓ Dobrze! Jedyna metoda płatności = duże ryzyko." :
                            "✗ Niepoprawnie. Brak bezpiecznych płatności to najgorszy sygnał."
            );

            feedbackQ2.setVisibility(View.VISIBLE);
            enableNextIfReady();
        });
    }

    private void enableNextIfReady() {
        btnNext.setEnabled(
                feedbackQ1.getVisibility() == View.VISIBLE &&
                        feedbackQ2.getVisibility() == View.VISIBLE
        );
    }
}
