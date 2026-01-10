package com.example.smartsenior.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.tts.TTSManager;

public abstract class BaseTTSActivity extends AppCompatActivity {

    // WAŻNE: teraz tts to Twój manager, więc istnieją: isEnabled(), speak(String), stop()
    protected TTSManager tts;

    public static final String PREFS_NAME = "app_prefs";
    public static final String PREF_TTS_ENABLED = "tts_enabled";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = TTSManager.get(this); // zawsze istnieje
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Jeżeli gdzieś jeszcze masz stary przycisk do TTS w layoutach – ukryj go
        setupTtsToggleIfPresent();

        // czytaj dopiero po wyrenderowaniu widoku
        getWindow().getDecorView().post(this::speakIfEnabled);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTts();
    }

    @Override
    protected void onDestroy() {
        // Nie wyłączamy globalnie TTS (shutdown) przy niszczeniu Activity – manager ma żyć globalnie.
        stopTts();
        super.onDestroy();
    }

    /** Bezpieczne – zostawiamy, żeby moduły mogły dalej robić tts.stop() */
    protected void stopTts() {
        if (tts != null) tts.stop();
    }

    /** Centralne czytanie – manager sam sprawdza enabled */
    protected void speakIfEnabled() {
        if (tts == null) return;
        String text = getSpeakText();
        if (text == null) return;

        text = text.trim();
        if (text.isEmpty()) return;

        tts.speak(text);
    }

    /**
     * Kompatybilność z tutorialami: wcześniej to wywoływałeś w kilku ekranach.
     * Teraz, skoro przełącznik ma być TYLKO w Settings, ukrywamy go, jeśli istnieje.
     */
    protected void setupTtsToggleIfPresent() {
        View v = findViewById(com.example.smartsenior.R.id.btnTtsToggle);
        if (v != null) {
            v.setVisibility(View.GONE);
            v.setEnabled(false);
        }
    }

    protected String collectSpeakableTextFromLayout() {
        View root = findViewById(android.R.id.content);
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        collectTextRecursive(root, sb);

        return sb.toString().replaceAll("\\s+", " ").trim();
    }

    private void collectTextRecursive(View v, StringBuilder sb) {
        if (v == null || v.getVisibility() != View.VISIBLE) return;

        if (v instanceof TextView && !(v instanceof Button) && !(v instanceof EditText)) {
            CharSequence cs = ((TextView) v).getText();
            if (cs != null) {
                String t = cs.toString().trim();
                if (!t.isEmpty()) {
                    if (sb.length() > 0) sb.append(". ");
                    sb.append(t);
                }
            }
        }

        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                collectTextRecursive(vg.getChildAt(i), sb);
            }
        }
    }

    protected abstract String getSpeakText();
}
