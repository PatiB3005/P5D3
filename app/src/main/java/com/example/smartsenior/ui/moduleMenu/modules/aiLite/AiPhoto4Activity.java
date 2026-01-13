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

public class AiPhoto4Activity extends BaseTTSActivity {

    private Button btnFake, btnReal;
    private MaterialButton btnNext;
    private ScrollView scrollView;
    private ResultPopup resultPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_photo4);

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
            startActivity(new Intent(this, AiPhoto5Activity.class));
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
                "To zdjęcie wygląda na wygenerowane przez sztuczną inteligencję. "
                        + "Nierealistyczna scena – całość przypomina plakat albo wizualizację, a nie naturalne ujęcie. "
                        + "Problemy z anatomią – dłonie, palce lub proporcje ciała mogą wyglądać nienaturalnie. "
                        + "Zbyt gładkie powierzchnie – materiały i skóra są mocno wygładzone, jakby przefiltrowane. "
                        + "Światło jest bardzo równe i katalogowe, z małą liczbą drobnych cieni i niedoskonałości. "
                        + "Takie połączenie elementów jest typowe dla obrazów generowanych przez modele AI.";

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
