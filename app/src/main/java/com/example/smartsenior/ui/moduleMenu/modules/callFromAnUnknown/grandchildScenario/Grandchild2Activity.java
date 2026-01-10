package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Grandchild2Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_2);

        TextView answerA = findViewById(R.id.textAnswerA);
        TextView answerB = findViewById(R.id.textAnswerB);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A -> od razu słuchasz historii -> scena 3
        answerA.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild2Activity.this, Grandchild3Activity.class);
            startActivity(intent);
        });

        // B -> "Który wnuczek?" -> scena 2a
        answerB.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild2Activity.this, Grandchild2aActivity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie scenariusza
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
