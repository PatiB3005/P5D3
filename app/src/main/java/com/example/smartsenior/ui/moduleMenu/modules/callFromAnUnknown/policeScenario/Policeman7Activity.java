package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;

public class Policeman7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_7);

        Button finish = findViewById(R.id.btnFinish);

        finish.setOnClickListener(v -> {
            // ZALICZENIE CZĘŚCI: scenariusz policjant ukończony
            ProgressStore.markDone(this, ProgressKeys.M2_POLICEMAN_DONE);
            finish();
        });
    }
}
