package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class TutorialActivity6 extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_6);

        setupTtsToggleIfPresent();

        Button finishButton = findViewById(R.id.button_zakoncz);
        finishButton.setOnClickListener(v -> {
            getSharedPreferences("tutorial_progress", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            tts.stop();
            Intent intent = new Intent(TutorialActivity6.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        TextView d1 = findViewById(R.id.text_dopisek1);
        TextView d2 = findViewById(R.id.text_dopisek2);

        StringBuilder sb = new StringBuilder();
        if (t != null && t.getText() != null) sb.append(t.getText()).append(". ");
        if (d != null && d.getText() != null) sb.append(d.getText()).append(" ");
        if (d1 != null && d1.getText() != null) sb.append(d1.getText()).append(" ");
        if (d2 != null && d2.getText() != null) sb.append(d2.getText());

        return sb.toString().trim();
    }
}
