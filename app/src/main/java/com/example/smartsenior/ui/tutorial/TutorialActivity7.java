package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class TutorialActivity7 extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_7);

        setupTtsToggleIfPresent();

        ImageButton btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.bringToFront();
        btnBackToMain.setTranslationZ(50f);

        btnBackToMain.setOnClickListener(v -> {
            if (tts != null) tts.stop();
            goToMainMenu();
        });

        Button nextButton = findViewById(R.id.button_dalej);
        nextButton.setOnClickListener(v -> {
            if (tts != null) tts.stop();
            startActivity(new Intent(TutorialActivity7.this, TutorialActivity8.class));
        });
    }

    private void goToMainMenu() {
        getSharedPreferences("tutorial_progress", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected String getSpeakText() {
        TextView tt = findViewById(R.id.text_tytul);
        TextView dd = findViewById(R.id.text_opis);

        CharSequence t = tt != null ? tt.getText() : null;
        CharSequence d = dd != null ? dd.getText() : null;

        StringBuilder sb = new StringBuilder();
        if (t != null) sb.append(t).append(". ");
        if (d != null) sb.append(d);

        return sb.toString().trim();
    }
}
