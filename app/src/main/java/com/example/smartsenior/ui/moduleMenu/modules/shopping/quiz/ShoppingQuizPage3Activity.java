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

public class ShoppingQuizPage3Activity extends BaseTTSActivity {

    private RadioGroup groupQ5, groupQ6;
    private MaterialButton btnNext;
    private int score;

    private boolean q5Answered = false;
    private boolean q6Answered = false;

    private FrameLayout popupOverlay;
    private TextView titleText;
    private LinearLayout popupList;
    private MaterialButton btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_page3);

        groupQ5 = findViewById(R.id.groupQ5);
        groupQ6 = findViewById(R.id.groupQ6);
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

        setupQ5();
        setupQ6();

        btnNext.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, ShoppingQuizPage4Activity.class);
            intent.putExtra("QUIZ_RESULT", score);
            startActivity(intent);
        });
    }

    private void lockGroup(RadioGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).setEnabled(false);
        }
    }

    private void setupQ5() {
        groupQ5.setOnCheckedChangeListener((group, checkedId) -> {
            if (q5Answered) return;
            q5Answered = true;

            tts.stop();
            lockGroup(groupQ5);

            boolean isCorrect = (checkedId == R.id.q5_b);
            if (isCorrect) score++;

            String explanation = isCorrect
                    ? "Dobrze! Brak płatności przy odbiorze to mocny sygnał ostrożności."
                    : "Niepoprawnie. Literówki czy cookies nie muszą oznaczać oszustwa, ważniejszy jest brak bezpiecznych form płatności.";

            showPopup(isCorrect, explanation);
        });
    }

    private void setupQ6() {
        groupQ6.setOnCheckedChangeListener((group, checkedId) -> {
            if (q6Answered) return;
            q6Answered = true;

            tts.stop();
            lockGroup(groupQ6);

            boolean isCorrect = (checkedId == R.id.q6_a);
            if (isCorrect) score++;

            String explanation = isCorrect
                    ? "Dobrze! Rozmazane zdjęcia są najmniej podejrzane – ktoś po prostu ma słaby aparat."
                    : "Niepoprawnie. Idealne lub pojedyncze zdjęcia mogą być skradzione z innej strony.";

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
        btnNext.setEnabled(q5Answered && q6Answered);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
