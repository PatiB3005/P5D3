package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class TutorialActivity3 extends BaseTTSActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_3);

        setupTtsToggleIfPresent();

        Button showModulesButton = findViewById(R.id.button_pokaz_moduly);
        showModulesButton.setOnClickListener(v -> {
            // Zapisz postęp, że użytkownik ukończył ekran 3
            SharedPreferences prefs = getSharedPreferences("tutorial_progress", MODE_PRIVATE);
            prefs.edit().putInt("last_completed", 3).apply();

            tts.stop();
            // Przejdź do ekranu głównego
            Intent intent = new Intent(TutorialActivity3.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected String getSpeakText() {
        CharSequence t = ((TextView) findViewById(R.id.text_tytul)).getText();
        CharSequence d = ((TextView) findViewById(R.id.text_opis)).getText();
        return (t == null ? "" : t + ". ") + (d == null ? "" : d.toString());
    }
}
