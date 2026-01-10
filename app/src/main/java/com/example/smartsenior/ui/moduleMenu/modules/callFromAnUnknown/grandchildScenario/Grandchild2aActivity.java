package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Grandchild2aActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_2a);

        TextView answerA = findViewById(R.id.textAnswerA);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A -> "Dobrze, mów szybko..." -> scena 3
        answerA.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild2aActivity.this, Grandchild3Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie
        declineCall.setOnClickListener(v -> {
            tts.stop();
            finish();
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
