package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Grandchild4Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_4);

        TextView answerA = findViewById(R.id.textAnswerA);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A -> przygotowujesz pieniądze -> scena 5
        answerA.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild4Activity.this, Grandchild5Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie
        declineCall.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild4Activity.this, Grandchild6PositiveActivity.class);
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
