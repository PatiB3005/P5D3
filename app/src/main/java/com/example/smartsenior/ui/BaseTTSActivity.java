package com.example.smartsenior.ui;

import android.view.View;
import android.widget.ImageButton;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.tts.TTSManager;

public abstract class BaseTTSActivity extends AppCompatActivity {

    protected TTSManager tts;
    protected abstract String getSpeakText();

    /** Podklasa zwraca true, jeśli ekran ma startować z WYŁĄCZONYM TTS. */
    protected boolean startWithTtsOff() { return false; }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = TTSManager.get(this);
    }

    // Centralne: po setContentView wymuś OFF (jeśli trzeba) i podepnij przycisk
    @Override public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        afterContentViewReady();
    }
    @Override public void setContentView(View view) {
        super.setContentView(view);
        afterContentViewReady();
    }
    private void afterContentViewReady() {
        if (startWithTtsOff()) {
            tts.setEnabled(false); // działa "po UI"
        }
        setupTtsToggleIfPresent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tts.isEnabled()) tts.speak(getSpeakText());
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }

    protected void setupTtsToggleIfPresent() {
        ImageButton btn = findViewById(R.id.btnTTS);
        if (btn == null) return;
        updateBtnIcon(btn);
        btn.setOnClickListener(v -> {
            boolean nowEnabled = tts.toggle();
            updateBtnIcon(btn);
            if (nowEnabled) tts.speak(getSpeakText());
            else tts.stop();
        });
    }

    private void updateBtnIcon(ImageButton btn) {
        boolean on = tts.isEnabled();
        btn.setImageResource(on ? R.drawable.ic_volume_on : R.drawable.ic_volume_off);
        btn.setContentDescription(getString(on ? R.string.tts_on : R.string.tts_off));
    }
}
