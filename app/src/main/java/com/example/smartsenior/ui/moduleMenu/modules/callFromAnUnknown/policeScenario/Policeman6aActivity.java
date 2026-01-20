package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Policeman6aActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_6a);

        Button next = findViewById(R.id.btnNext);

        // A → scene 7
        next.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(this, Policeman7Activity.class);
            startActivity(intent);
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
