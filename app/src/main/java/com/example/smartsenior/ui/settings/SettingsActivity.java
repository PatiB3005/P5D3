package com.example.smartsenior.ui.settings;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.smartsenior.R;
import com.example.smartsenior.tts.TTSManager;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchFontSize;
    private Button btnTtsToggle;

    private boolean isLarge;
    private boolean isTtsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        switchFontSize = findViewById(R.id.switchFontSize);
        btnTtsToggle = findViewById(R.id.btnTtsToggle);

        var prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        isLarge = prefs.getBoolean("large_font", false);

        // TTS – przez manager
        TTSManager tts = TTSManager.get(this);
        isTtsEnabled = tts.isEnabled();
        updateTtsButtonText();

        // Font switch
        switchFontSize.setChecked(isLarge);
        switchFontSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isLarge = isChecked;
            prefs.edit().putBoolean("large_font", isLarge).apply();
        });

        // TTS button
        btnTtsToggle.setOnClickListener(v -> {
            isTtsEnabled = tts.toggle();
            updateTtsButtonText();
            if (isTtsEnabled) tts.speak("Lektor włączony");
        });
    }

    private void updateTtsButtonText() {
        btnTtsToggle.setText(isTtsEnabled ? "Wyłącz lektora" : "Włącz lektora");
    }
}
