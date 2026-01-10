package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class Grandchild6PositiveActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_6_positive);

        Button next = findViewById(R.id.btnNext);

        // Dalej -> to samo POUCZENIE co po negatywnym wyniku
        next.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(Grandchild6PositiveActivity.this, Grandchild7Activity.class);
            startActivity(intent);
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
