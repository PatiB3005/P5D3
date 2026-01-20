package com.example.smartsenior.ui.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.example.smartsenior.R;
import com.example.smartsenior.tts.TTSManager;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchFontSize;
    private SwitchCompat switchTts;

    private boolean isLarge;
    private boolean isTtsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);

        // ⬇⬇⬇ TO JEST KLUCZ
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        switchFontSize = findViewById(R.id.switchFontSize);
        switchTts = findViewById(R.id.btnTtsToggle);

        var prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        isLarge = prefs.getBoolean("large_font", false);

        // TTS
        TTSManager tts = TTSManager.get(this);
        isTtsEnabled = tts.isEnabled();

        // FONT
        switchFontSize.setChecked(isLarge);
        switchFontSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isLarge = isChecked;
            prefs.edit().putBoolean("large_font", isLarge).apply();
        });

        // TTS
        switchTts.setChecked(isTtsEnabled);
        switchTts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked != isTtsEnabled) {
                isTtsEnabled = tts.toggle();
            } else {
                isTtsEnabled = isChecked;
            }

            if (isTtsEnabled && isChecked) {
                tts.speak("Lektor włączony");
            }
        });
    }
}
