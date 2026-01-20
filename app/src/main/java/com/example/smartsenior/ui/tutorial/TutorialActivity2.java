package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class TutorialActivity2 extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_2);

        setupTtsToggleIfPresent();

        ImageButton btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            tts.stop();
            goToMainMenu();
        });

        Button nextButton = findViewById(R.id.button_dalej);
        nextButton.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(TutorialActivity2.this, TutorialActivity3.class));
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
