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
        btnBackToMain.setOnClickListener(v -> {
            tts.stop();
            goToMainMenu();
        });

        Button nextButton = findViewById(R.id.button_dalej);
        nextButton.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(TutorialActivity7.this, TutorialActivity8.class));
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
        CharSequence t = ((TextView) findViewById(R.id.text_tytul)).getText();
        CharSequence d = ((TextView) findViewById(R.id.text_opis)).getText();
        return (t == null ? "" : t + ". ") + (d == null ? "" : d.toString());
    }
}
