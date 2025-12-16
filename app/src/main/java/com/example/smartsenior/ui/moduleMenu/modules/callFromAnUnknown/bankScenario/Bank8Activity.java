package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.CallFromAnUnknownMenuActivity;

public class Bank8Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_8);

        Button btnFinish = findViewById(R.id.btnFinish);

        btnFinish.setOnClickListener(v -> {
            // ZALICZENIE CZĘŚCI: scenariusz bank ukończony
            ProgressStore.markDone(this, ProgressKeys.M2_BANK_DONE);

            Intent intent = new Intent(Bank8Activity.this, CallFromAnUnknownMenuActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
