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

public class TutorialActivity1 extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("tutorial_progress", MODE_PRIVATE);
        int lastCompleted = prefs.getInt("last_completed", 0);
        if (lastCompleted == 3) {
            startActivity(new Intent(this, TutorialActivity4.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_tutorial_1);

        // TTS – ujednolicone (wcześniej tu brakowało)
        setupTtsToggleIfPresent();

        ImageButton btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            tts.stop();
            goToMainMenu();
        });

        Button startButton = findViewById(R.id.button_zaczynamy);
        startButton.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(TutorialActivity1.this, TutorialActivity2.class));
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
        TextView h = findViewById(R.id.text_hint);

        StringBuilder sb = new StringBuilder();
        if (t != null && t.getText() != null) sb.append(t.getText()).append(". ");
        if (d != null && d.getText() != null) sb.append(d.getText()).append(" ");
        if (h != null && h.getText() != null) sb.append(h.getText());

        return sb.toString().trim();
    }
}
