package com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.FrameLayout;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class ShoppingQuizPage4Activity extends BaseTTSActivity {

    private RadioGroup groupQ1, groupQ2;
    private MaterialButton btnNext;
    private int score;

    private boolean q1Answered = false;
    private boolean q2Answered = false;

    private FrameLayout popupOverlay;
    private TextView titleText;
    private LinearLayout popupList;
    private MaterialButton btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_page4);

        groupQ1 = findViewById(R.id.groupQ1);
        groupQ2 = findViewById(R.id.groupQ2);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);

        score = getIntent().getIntExtra("QUIZ_RESULT", 0);

        popupOverlay = findViewById(R.id.popupOverlay);
        titleText = popupOverlay.findViewById(R.id.titleText);
        popupList = popupOverlay.findViewById(R.id.popupList);
        btnOk = popupOverlay.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(v -> {
            popupOverlay.setVisibility(View.GONE);
            enableNextIfReady();
        });

        setupQ1();
        setupQ2();

        btnNext.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, ShoppingQuizSummaryActivity.class);
            intent.putExtra("QUIZ_RESULT", score);
            startActivity(intent);
        });
    }

    private void lockGroup(RadioGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).setEnabled(false);
        }
    }

    private void setupQ1() {
        groupQ1.setOnCheckedChangeListener((group, checkedId) -> {
            if (q1Answered) return;
            q1Answered = true;

            tts.stop();
            lockGroup(groupQ1);

            boolean isCorrect = (checkedId == R.id.q1_b);
            if (isCorrect) score++;

            String explanation = isCorrect
                    ? "Dobrze! Identyczne, bardzo podobne opinie w krótkim czasie to często fałszywe recenzje."
                    : "Niepoprawnie. Najgroźniejsze są opinie pisane w tym samym stylu i czasie.";

            showPopup(isCorrect, explanation);
        });
    }

    private void setupQ2() {
        groupQ2.setOnCheckedChangeListener((group, checkedId) -> {
            if (q2Answered) return;
            q2Answered = true;

            tts.stop();
            lockGroup(groupQ2);

            boolean isCorrect = (checkedId == R.id.q2_a);
            if (isCorrect) score++;

            String explanation = isCorrect
                    ? "Dobrze! Jedyna metoda płatności i brak bezpiecznych opcji to bardzo duże ryzyko."
                    : "Niepoprawnie. Największym sygnałem ostrzegawczym jest brak bezpiecznych form płatności.";

            showPopup(isCorrect, explanation);
        });
    }

    private void showPopup(boolean isCorrect, String explanation) {
        titleText.setText(isCorrect ? "Poprawna odpowiedź" : "Niepoprawna odpowiedź");
        popupList.removeAllViews();

        TextView tv = new TextView(this);
        tv.setText(explanation);
        tv.setTextSize(18f);
        tv.setTextColor(0xFF1A202C);
        tv.setPadding(0, 0, 0, 0);
        tv.setTypeface(getResources().getFont(R.font.montserrat_regular));
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setFontFeatureSettings(String.valueOf(getResources().getFont(R.font.montserrat_regular)));

        popupList.addView(tv);
        popupOverlay.setVisibility(View.VISIBLE);
    }

    private void enableNextIfReady() {
        btnNext.setEnabled(q1Answered && q2Answered);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
