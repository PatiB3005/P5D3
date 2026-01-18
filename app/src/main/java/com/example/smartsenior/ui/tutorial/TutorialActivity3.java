package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;

public class TutorialActivity3 extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_3);

        setupTtsToggleIfPresent();

        Button showModulesButton = findViewById(R.id.button_pokaz_moduly);
        showModulesButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("tutorial_progress", MODE_PRIVATE);
            prefs.edit().putInt("last_completed", 3).apply();

            tts.stop();

            Intent intent = new Intent(TutorialActivity3.this, ModuleMenuActivity.class);
            intent.putExtra("from_tutorial", true);
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
