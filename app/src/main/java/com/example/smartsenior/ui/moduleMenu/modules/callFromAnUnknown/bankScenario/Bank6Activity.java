package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Bank6Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_6);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        textAnswerA.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(Bank6Activity.this, Bank7Activity.class));
        });

        // Czerwona słuchawka → result
        declineCall.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, BankPositiveResultActivity.class);
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
