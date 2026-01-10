package com.example.smartsenior.ui.settings;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartsenior.R;
import com.example.smartsenior.tts.TTSManager;

public class SettingsActivity extends AppCompatActivity {

    private Button btnFontSizeToggle;
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

        btnFontSizeToggle = findViewById(R.id.btnFontSizeToggle);
        btnTtsToggle = findViewById(R.id.btnTtsToggle);

        // Font – zostawiam jak miałeś (na prefsach), bo nie pokazałeś reszty logiki aplikacji.
        var prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        isLarge = prefs.getBoolean("large_font", false);

        // TTS – przez manager
        TTSManager tts = TTSManager.get(this);
        isTtsEnabled = tts.isEnabled();

        updateFontButtonText();
        updateTtsButtonText();

        btnFontSizeToggle.setOnClickListener(v -> {
            isLarge = !isLarge;
            prefs.edit().putBoolean("large_font", isLarge).apply();
            updateFontButtonText();
        });

        btnTtsToggle.setOnClickListener(v -> {
            isTtsEnabled = tts.toggle();
            updateTtsButtonText();

            // opcjonalnie: krótkie potwierdzenie głosem po włączeniu
            if (isTtsEnabled) {
                tts.speak("Lektor włączony");
            }
        });
    }

    private void updateFontButtonText() {
        btnFontSizeToggle.setText(isLarge ? "Pomniejsz tekst" : "Powiększ tekst");
    }

    private void updateTtsButtonText() {
        btnTtsToggle.setText(isTtsEnabled ? "Wyłącz lektora" : "Włącz lektora");
    }
}
