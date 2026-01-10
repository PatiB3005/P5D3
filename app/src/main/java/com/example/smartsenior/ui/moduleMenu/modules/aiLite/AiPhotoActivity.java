package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class AiPhotoActivity extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_photo1);

        MaterialButton next = findViewById(R.id.btnNext);

        next.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(this, AiPhoto2Activity.class));
        });

        ScoreManageAi.reset();
    }

    @Override
    protected String getSpeakText() {
        // Tu zwykle są opisy w TextView, a przycisk "Dalej" pomijamy (Button).
        return collectSpeakableTextFromLayout();
    }
}
