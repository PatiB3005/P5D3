package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class TutorialActivity8 extends BaseTTSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_8);

        setupTtsToggleIfPresent();

        ImageButton btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            tts.stop();
            goToMainMenu();
        });

        Button nextButton = findViewById(R.id.button_dalej);
        nextButton.setOnClickListener(v -> {
            tts.stop();
            // TU przechodzisz do istniejącego ekranu "Gratulacje!" = TutorialActivity6
            startActivity(new Intent(TutorialActivity8.this, TutorialActivity6.class));
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
        CharSequence h = null;
        TextView hint = findViewById(R.id.text_hint);
        if (hint != null) h = hint.getText();

        StringBuilder sb = new StringBuilder();
        if (t != null) sb.append(t).append(". ");
        if (d != null) sb.append(d);
        if (h != null) sb.append(". ").append(h);

        return sb.toString().trim();
    }
}
