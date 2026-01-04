package com.example.smartsenior.ui.moduleMenu.modules.fakenews.theory;

import android.os.Bundle;
import android.widget.ImageButton;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class FakeNewsTheoryActivity extends AppCompatActivity {

    private boolean ttsOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_theory);

        // Dalej
        findViewById(R.id.btnNext).setOnClickListener(v -> {
            Intent intent = new Intent(FakeNewsTheoryActivity.this, FakeNewsTheoryActivity_2.class);
            startActivity(intent);
        });

        // TTS toggle (na razie tylko ikonka)
        ImageButton btnTTS = findViewById(R.id.btnTTS);
        btnTTS.setOnClickListener(v -> {
            ttsOn = !ttsOn;
            btnTTS.setImageResource(ttsOn ? R.drawable.ic_volume_on : R.drawable.ic_volume_off);
            btnTTS.setContentDescription(getString(ttsOn ? R.string.tts_on : R.string.tts_off));
        });
    }
}
