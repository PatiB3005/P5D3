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

public class AiPhoto3Activity extends BaseTTSActivity {

    private Button btnFake, btnReal;
    private MaterialButton btnNext;
    private ScrollView scrollView;
    private ResultPopup resultPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_photo3);

        btnFake = findViewById(R.id.btnFake);
        btnReal = findViewById(R.id.btnReal);
        btnNext = findViewById(R.id.btnNext);
        scrollView = findViewById(R.id.scrollView);

        resultPopup = new ResultPopup(this);

        setButtonState(btnNext, false);

        // poprawna jest "Prawdziwe" (btnReal)
        btnReal.setOnClickListener(v -> handleAnswer(true));
        btnFake.setOnClickListener(v -> handleAnswer(false));

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, AiPhoto4Activity.class));
        });
    }

    private void handleAnswer(boolean isCorrect) {
        btnFake.setClickable(false);
        btnReal.setClickable(false);
        setButtonState(btnNext, true);
        btnNext.setVisibility(View.VISIBLE);

        btnReal.setAlpha(isCorrect ? 1f : 0.3f);
        btnFake.setAlpha(isCorrect ? 0.3f : 1f);

        if (isCorrect) {
            ScoreManageAi.addPoint();
        }

        String explanation =
                "To zdjęcie jest prawdziwe, ponieważ ma naturalną strukturę futra, wąsów i oczu. "
                        + "Losowa, drobna struktura futra – włoski mają naturalny chaos: różne długości, kierunki, gęstość i mikro‑cienie. "
                        + "AI często robi futro zbyt jednolite albo z powtarzalnym wzorem. "
                        + "Wąsy są cienkie i nieregularne – mają różną grubość, lekkie załamania i zanikają w tle, nie wyglądają jak dorzucone linie. "
                        + "Oczy mają realistyczne refleksy – w tęczówce i źrenicy widać naturalne odbicie światła i głębię. "
                        + "AI bywa niespójne z odbiciami i kształtem źrenic. "
                        + "Spójne światło i cienie na pysku – rozkład jasnych i ciemnych miejsc na nosie, policzku i pod okiem wygląda jak z aparatu, bez dziwnych załamań.";



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
