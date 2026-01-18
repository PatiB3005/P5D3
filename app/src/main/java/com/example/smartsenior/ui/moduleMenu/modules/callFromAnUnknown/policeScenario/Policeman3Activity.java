package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;

public class Policeman3Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_3);

        TextView answerA = findViewById(R.id.textAnswerA);
        TextView answerB = findViewById(R.id.textAnswerB);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A → scene 4
        answerA.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, Policeman4Activity.class);
            startActivity(intent);
        });

        // B → result
        answerB.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, Policeman6bActivity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka → result
        declineCall.setOnClickListener(v -> {
            tts.stop();

            Intent intent = new Intent(Policeman3Activity.this, Policeman6bActivity.class);
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
