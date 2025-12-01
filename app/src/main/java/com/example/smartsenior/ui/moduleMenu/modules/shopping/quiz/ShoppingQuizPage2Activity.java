package com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class ShoppingQuizPage2Activity extends AppCompatActivity {

    private RadioGroup groupQ3, groupQ4;
    private TextView feedbackQ3, feedbackQ4;
    private MaterialButton btnNext;

    private int score; // wynik przejęty z poprzedniej strony

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_page2);

        groupQ3 = findViewById(R.id.groupQ3);
        groupQ4 = findViewById(R.id.groupQ4);
        feedbackQ3 = findViewById(R.id.feedbackQ3);
        feedbackQ4 = findViewById(R.id.feedbackQ4);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setEnabled(false);

        score = getIntent().getIntExtra("QUIZ_RESULT", 0);

        setupQ3();
        setupQ4();

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingQuizPage3Activity.class);
            intent.putExtra("QUIZ_RESULT", score);
            startActivity(intent);
        });
    }

    private void lockGroup(RadioGroup group) {
        for (int i = 0; i < group.getChildCount(); i++)
            group.getChildAt(i).setEnabled(false);
    }

    private void setupQ3() {
        groupQ3.setOnCheckedChangeListener((group, checkedId) -> {
            lockGroup(groupQ3);

            if (checkedId == R.id.q3_b) score++;

            feedbackQ3.setText(
                    checkedId == R.id.q3_b ?
                            "✓ Dobrze! Ogólnikowy opis to częsty znak scamu." :
                            "✗ Niepoprawnie. Najbardziej podejrzane są opisy reklamowe bez konkretów."
            );

            feedbackQ3.setVisibility(View.VISIBLE);
            enableNextIfReady();
        });
    }

    private void setupQ4() {
        groupQ4.setOnCheckedChangeListener((group, checkedId) -> {
            lockGroup(groupQ4);

            if (checkedId == R.id.q4_a) score++;

            feedbackQ4.setText(
                    checkedId == R.id.q4_a ?
                            "✓ Dobrze! Reklama z nieznanego źródła jest bardzo ryzykowna." :
                            "✗ Niepoprawnie. Najgroźniejsza jest reklama z podejrzanego źródła."
            );

            feedbackQ4.setVisibility(View.VISIBLE);
            enableNextIfReady();
        });
    }

    private void enableNextIfReady() {
        btnNext.setEnabled(
                feedbackQ3.getVisibility() == View.VISIBLE &&
                        feedbackQ4.getVisibility() == View.VISIBLE
        );
    }
}
