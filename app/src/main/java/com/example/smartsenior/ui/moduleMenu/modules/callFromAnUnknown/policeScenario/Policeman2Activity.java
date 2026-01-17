package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;

public class Policeman2Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_2);

        TextView answerA = findViewById(R.id.textAnswerA);
        TextView answerB = findViewById(R.id.textAnswerB);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        answerA.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, Policeman3Activity.class);
            startActivity(intent);
        });

        answerB.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, Policeman2aActivity.class);
            startActivity(intent);
        });

        declineCall.setOnClickListener(v -> {
            tts.stop();

            Intent intent = new Intent(Policeman2Activity.this, ModuleMenuActivity.class);
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
