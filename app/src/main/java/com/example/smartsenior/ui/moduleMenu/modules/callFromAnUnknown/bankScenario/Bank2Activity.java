package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario.Grandchild5Activity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario.Grandchild6PositiveActivity;

public class Bank2Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_2);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        textAnswerA.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(Bank2Activity.this, Bank3Activity.class));
        });

        declineCall.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Bank2Activity.this, BankPositiveResultActivity.class);
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
