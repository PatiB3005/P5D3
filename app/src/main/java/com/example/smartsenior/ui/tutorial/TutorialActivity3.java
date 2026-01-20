package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;

public class TutorialActivity3 extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_3);

        setupTtsToggleIfPresent();

        ImageButton btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            tts.stop();
            goToMainMenu();
        });

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

    private void goToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected String getSpeakText() {
        TextView t = findViewById(R.id.text_tytul);
        TextView d = findViewById(R.id.text_opis);

        StringBuilder sb = new StringBuilder();
        if (t != null && t.getText() != null) sb.append(t.getText()).append(". ");
        if (d != null && d.getText() != null) sb.append(d.getText());

        return sb.toString().trim();
    }
}
