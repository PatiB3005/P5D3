package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Bank4aActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_4a);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        TextView textAnswerB = findViewById(R.id.textAnswerB);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        textAnswerA.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(Bank4aActivity.this, Bank5Activity.class));
        });

        textAnswerB.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(Bank4aActivity.this, Bank8Activity.class));
        });

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
