package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class TutorialActivity1 extends BaseTTSActivity {

    @Override
    protected boolean startWithTtsOff() { return true; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("tutorial_progress", MODE_PRIVATE);
        int lastCompleted = prefs.getInt("last_completed", 0);
        if (lastCompleted == 3) {
            startActivity(new Intent(this, TutorialActivity4.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_tutorial_1);

        Button startButton = findViewById(R.id.button_zaczynamy);
        startButton.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(TutorialActivity1.this, TutorialActivity2.class));
        });
    }

    @Override
    protected String getSpeakText() {
        CharSequence t = ((TextView) findViewById(R.id.text_tytul)).getText();
        CharSequence d = ((TextView) findViewById(R.id.text_opis)).getText();
        return (t == null ? "" : t + ". ") + (d == null ? "" : d.toString());
    }
}
