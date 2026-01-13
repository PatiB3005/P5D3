package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.ResultPopup;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class AiPhoto6Activity extends BaseTTSActivity {

    private Button btnFake, btnReal;
    private MaterialButton btnNext;
    private ScrollView scrollView;
    private ResultPopup resultPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_photo6);

        btnFake = findViewById(R.id.btnFake);
        btnReal = findViewById(R.id.btnReal);
        btnNext = findViewById(R.id.btnNext);
        scrollView = findViewById(R.id.scrollView);

        resultPopup = new ResultPopup(this);

        setButtonState(btnNext, false);

        // poprawna jest "Prawdziwe" (btnReal)
        btnReal.setOnClickListener(v -> handleAnswer(true));
        btnFake.setOnClickListener(v -> handleAnswer(false));

        // przycisk Next może być ukryty / nieużywany, bo przejście robimy po popupie
        btnNext.setOnClickListener(v -> {
            // opcjonalnie: dodatkowe przejście, ale główne będzie w onDismiss
        });
    }

    private void handleAnswer(boolean isCorrect) {
        btnFake.setClickable(false);
        btnReal.setClickable(false);
        setButtonState(btnNext, true);
        btnNext.setVisibility(View.VISIBLE);

        btnFake.setAlpha(isCorrect ? 0.3f : 1f);
        btnReal.setAlpha(isCorrect ? 1f : 0.3f);

        if (isCorrect) {
            ScoreManageAi.addPoint();
        }

        String explanation =
                "To zdjęcie jest prawdziwe. "
                        + "Naturalna nieidealność pączków – każdy ma odrobinę inny kształt, inną ilość lukru i inną szerokość jasnej obwódki. "
                        + "AI często tworzy rzeczy zbyt równe albo z powtarzalnym wzorem. "
                        + "Lukier zachowuje się realistycznie – widać nierówne zacieki, prześwity i różną grubość warstwy; tam, gdzie jest cień, lukier wygląda inaczej. "
                        + "To fizycznie złożone i trudniejsze do wiarygodnego odwzorowania dla modeli AI. "
                        + "Nieregularne rozmieszczenie posypki – drobinki są rozmieszczone chaotycznie, a nie w równym, sekwencyjnym wzorze. "
                        + "Spójne światło i cienie – kierunek światła jest konsekwentny na wszystkich pączkach i na talerzu, a cienie kontaktowe w miejscach styku z tacą wyglądają naturalnie.";


        resultPopup.setOnDismissListener(() -> {
            int score = ScoreManageAi.getScore();   // dopasuj do swojej implementacji
            int maxScore = 6;                       // jeśli masz 6 ekranów

            Intent intent = new Intent(this, AiResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("maxScore", maxScore);
            startActivity(intent);
            finish();
        });

        resultPopup.show(isCorrect, explanation, "Zobacz wynik");
    }

    private void setButtonState(MaterialButton button, boolean enabled) {
        button.setEnabled(enabled);
        button.setAlpha(enabled ? 1f : 0.4f);
    }

    @Override
    protected String getSpeakText() {
        String base = collectSpeakableTextFromLayout();

        String a = (btnFake != null && btnFake.getText() != null)
                ? btnFake.getText().toString().trim()
                : "";
        String b = (btnReal != null && btnReal.getText() != null)
                ? btnReal.getText().toString().trim()
                : "";

        StringBuilder sb = new StringBuilder();
        if (!base.isEmpty()) sb.append(base);
        if (!a.isEmpty()) {
            if (sb.length() == 0) sb.append(".");
            sb.append(" Opcja pierwsza ").append(a);
        }
        if (!b.isEmpty()) {
            if (sb.length() == 0) sb.append(".");
            sb.append(" Opcja druga ").append(b);
        }
        return sb.toString().trim();
    }
}
