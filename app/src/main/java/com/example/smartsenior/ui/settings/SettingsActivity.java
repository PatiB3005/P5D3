package com.example.smartsenior.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.smartsenior.R;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchFontSize;
    private boolean isLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        switchFontSize = findViewById(R.id.switchFontSize);

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        isLarge = prefs.getBoolean("large_font", false);

        switchFontSize.setChecked(isLarge);

        switchFontSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isLarge = isChecked;
            prefs.edit().putBoolean("large_font", isLarge).apply();
        });
    }
}
