package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Grandchild3Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_3);

        TextView answerA = findViewById(R.id.textAnswerA);
        TextView answerB = findViewById(R.id.textAnswerB);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A -> idziesz za "wnuczkiem" -> dalszy scam (scena 4)
        answerA.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild3Activity.this, Grandchild4Activity.class);
            startActivity(intent);
        });

        // B -> dzwonisz do rodziny/policji -> POZYTYWNE podsumowanie
        answerB.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild3Activity.this, Grandchild6PositiveActivity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – zakończenie bez podsumowania
        declineCall.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild3Activity.this, Grandchild6PositiveActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
