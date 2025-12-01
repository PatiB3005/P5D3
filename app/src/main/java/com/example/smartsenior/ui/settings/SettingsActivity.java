package com.example.smartsenior.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartsenior.R;

public class SettingsActivity extends AppCompatActivity {

    private Button btnFontSizeToggle;
    private boolean isLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        btnFontSizeToggle = findViewById(R.id.btnFontSizeToggle);

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        isLarge = prefs.getBoolean("large_font", false);
        updateButtonText();

        btnFontSizeToggle.setOnClickListener(v -> {
            isLarge = !isLarge;
            prefs.edit().putBoolean("large_font", isLarge).apply();
            updateButtonText();
        });
    }

    private void updateButtonText() {
        if (isLarge) {
            btnFontSizeToggle.setText("Pomniejsz tekst");
        } else {
            btnFontSizeToggle.setText("Powiększ tekst");
        }
    }
}
