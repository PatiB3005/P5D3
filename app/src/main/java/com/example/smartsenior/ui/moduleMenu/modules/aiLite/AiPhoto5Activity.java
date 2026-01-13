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

public class AiPhoto5Activity extends BaseTTSActivity {

    private Button btnFake, btnReal;
    private MaterialButton btnNext;
    private ScrollView scrollView;
    private ResultPopup resultPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_photo5);

        btnFake = findViewById(R.id.btnFake);
        btnReal = findViewById(R.id.btnReal);
        btnNext = findViewById(R.id.btnNext);
        scrollView = findViewById(R.id.scrollView);

        resultPopup = new ResultPopup(this);

        setButtonState(btnNext, false);

        // poprawna jest AI (btnFake)
        btnFake.setOnClickListener(v -> handleAnswer(true));
        btnReal.setOnClickListener(v -> handleAnswer(false));

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, AiPhoto6Activity.class));
        });
    }

    private void handleAnswer(boolean isCorrect) {
        btnFake.setClickable(false);
        btnReal.setClickable(false);
        setButtonState(btnNext, true);
        btnNext.setVisibility(View.VISIBLE);

        btnFake.setAlpha(isCorrect ? 1f : 0.3f);
        btnReal.setAlpha(isCorrect ? 0.3f : 1f);

        if (isCorrect) {
            ScoreManageAi.addPoint();
        }

        String explanation =
                "To zdjęcie ma wiele cech obrazu wygenerowanego przez sztuczną inteligencję. "
                        + "Nierealistyczne fałdy materiału – spódnica ma idealnie gładkie, malarskie przejścia cieni i bardzo równe, szerokie fale. "
                        + "W prawdziwej tkaninie widać zwykle więcej drobnych zagnieceń i mniej perfekcyjnie równych gradientów. "
                        + "Postać jest niekompletna – u góry brakuje szyi i głowy, a u dołu widać tylko jedną nogę albo ciało jest ucięte w nienaturalnym miejscu, co bardzo często zdarza się AI. "
                        + "Podejrzanie spójne światło – cała sukienka, sofa i dodatki mają bardzo katalogowe, równe oświetlenie bez drobnych niedoskonałości i cieni kontaktowych typowych dla prawdziwego zdjęcia. "
                        + "Scena jest zbyt katalogowa – wszystko jest perfekcyjnie czyste i idealne, jakby z reklamy.";


        resultPopup.setOnDismissListener(() ->
                scrollView.post(() -> scrollView.smoothScrollTo(0, btnNext.getBottom()))
        );

        resultPopup.show(isCorrect, explanation, "OK");
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
