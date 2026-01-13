package com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.FrameLayout;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class ShoppingQuizPage1Activity extends BaseTTSActivity {

    private RadioGroup groupQ1, groupQ2;
    private MaterialButton btnNext;
    private int score = 0;

    private boolean q1Answered = false;
    private boolean q2Answered = false;

    // elementy popupu
    private FrameLayout popupOverlay;      // include id=popupOverlay
    private TextView titleText;
    private LinearLayout popupList;
    private MaterialButton btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_page1);

        groupQ1 = findViewById(R.id.groupQ1);
        groupQ2 = findViewById(R.id.groupQ2);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);

        // inicjalizacja popupu
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
            Intent intent = new Intent(this, ShoppingQuizPage2Activity.class);
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
                    ? "To prawidłowa odpowiedź. Sprawdzenie certyfikatu i szczegółów po kliknięciu w kłódkę jest najbezpieczniejsze."
                    : "To nie jest najlepsza odpowiedź. Samo HTTPS nie wystarczy – kliknij kłódkę i sprawdź szczegóły certyfikatu.";

            showPopup(isCorrect, explanation);
        });
    }

    private void setupQ2() {
        groupQ2.setOnCheckedChangeListener((group, checkedId) -> {
            if (q2Answered) return;
            q2Answered = true;

            tts.stop();
            lockGroup(groupQ2);

            boolean isCorrect = (checkedId == R.id.q2_c);
            if (isCorrect) score++;

            String explanation = isCorrect
                    ? "Dobrze! Fałszywy adres lub nieistniejąca lokalizacja to bardzo mocny sygnał oszustwa."
                    : "Nie do końca. Najbardziej podejrzana jest nieistniejąca lub fałszywa lokalizacja sklepu.";

            showPopup(isCorrect, explanation);
        });
    }

    private void showPopup(boolean isCorrect, String explanation) {
        // tytuł
        titleText.setText(isCorrect ? "Poprawna odpowiedź" : "Niepoprawna odpowiedź");
        // czyścimy treść
        popupList.removeAllViews();

        TextView tv = new TextView(this);
        tv.setText(explanation);
        tv.setTextSize(18f);
        tv.setTextColor(0xFF1A202C);
        tv.setPadding(0, 0, 0, 0);
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
