package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class TutorialActivity4 extends BaseTTSActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_4);

        setupTtsToggleIfPresent();

        Button nextButton = findViewById(R.id.button_dalej);
        nextButton.setOnClickListener(v -> {
            tts.stop();
            Intent intent = new Intent(TutorialActivity4.this, TutorialActivity5.class);
            startActivity(intent);
        });
    }

    @Override
    protected String getSpeakText() {
        CharSequence t = ((TextView) findViewById(R.id.text_tytul)).getText();
        CharSequence d = ((TextView) findViewById(R.id.text_opis)).getText();
        return (t == null ? "" : t + ". ") + (d == null ? "" : d.toString());
    }
}
