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

public class ShoppingQuizPage2Activity extends BaseTTSActivity {

    private RadioGroup groupQ3, groupQ4;
    private MaterialButton btnNext;
    private int score;

    private boolean q3Answered = false;
    private boolean q4Answered = false;

    // popup
    private FrameLayout popupOverlay;
    private TextView titleText;
    private LinearLayout popupList;
    private MaterialButton btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_page2);

        groupQ3 = findViewById(R.id.groupQ3);
        groupQ4 = findViewById(R.id.groupQ4);
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

        setupQ3();
        setupQ4();

        btnNext.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, ShoppingQuizPage3Activity.class);
            intent.putExtra("QUIZ_RESULT", score);
            startActivity(intent);
        });
    }

    private void lockGroup(RadioGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).setEnabled(false);
        }
    }

    private void setupQ3() {
        groupQ3.setOnCheckedChangeListener((group, checkedId) -> {
            if (q3Answered) return;
            q3Answered = true;

            tts.stop();
            lockGroup(groupQ3);

            boolean isCorrect = (checkedId == R.id.q3_b);
            if (isCorrect) score++;

            String explanation = isCorrect
                    ? "Dobrze! Ogólnikowy, reklamowy opis bez konkretów często oznacza scam."
                    : "Niepoprawnie. Najbardziej podejrzane są opisy pełne reklamy, ale bez dokładnych informacji.";

            showPopup(isCorrect, explanation);
        });
    }

    private void setupQ4() {
        groupQ4.setOnCheckedChangeListener((group, checkedId) -> {
            if (q4Answered) return;
            q4Answered = true;

            tts.stop();
            lockGroup(groupQ4);

            boolean isCorrect = (checkedId == R.id.q4_a);
            if (isCorrect) score++;

            String explanation = isCorrect
                    ? "Dobrze! Reklama z podejrzanego źródła może prowadzić na fałszywy sklep."
                    : "Niepoprawnie. Najgroźniejsze są reklamy z nieznanych lub podejrzanych stron.";

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
        btnNext.setEnabled(q3Answered && q4Answered);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
