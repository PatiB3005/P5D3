package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.CallFromAnUnknownMenuActivity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario.Grandchild5Activity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario.Grandchild6PositiveActivity;

public class BankNegativeResultActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_negative_result);

        Button next = findViewById(R.id.btnNext);


        next.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(BankNegativeResultActivity.this, Bank8Activity.class);
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
