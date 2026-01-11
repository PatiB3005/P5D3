package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Policeman5Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_5);

        TextView answerA = findViewById(R.id.textAnswerA);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A → Policeman6Activity
        answerA.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, Policeman6Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka → KONIEC
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
