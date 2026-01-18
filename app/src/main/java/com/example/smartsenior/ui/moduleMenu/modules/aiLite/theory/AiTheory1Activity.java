package com.example.smartsenior.ui.moduleMenu.modules.aiLite.theory;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class AiTheory1Activity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_theory1);

        MaterialButton next = findViewById(R.id.btnNext);

        next.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, AiTheory2Activity.class));
        });

        // zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
