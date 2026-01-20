package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.CallFromAnUnknownMenuActivity;

public class Bank8Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_8);

        Button btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(v -> {
            // Progress
            ProgressStore.markDone(this, ProgressKeys.M2_BANK_DONE);

            tts.stop();
            startActivity(new Intent(Bank8Activity.this, CallFromAnUnknownMenuActivity.class));
            finish();
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
