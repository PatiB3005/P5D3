package com.example.smartsenior.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.tts.TTSManager;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTTSActivity extends AppCompatActivity {

    protected TTSManager tts;
    protected abstract String getSpeakText();

    /** Podklasa zwraca true, jeśli ekran ma startować z WYŁĄCZONYM TTS. */
    protected boolean startWithTtsOff() { return false; }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = TTSManager.get(this);
    }

    // Centralne: po setContentView wymuś OFF (jeśli trzeba) i podepnij przycisk
    @Override public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        afterContentViewReady();
    }

    @Override public void setContentView(View view) {
        super.setContentView(view);
        afterContentViewReady();
    }

    private void afterContentViewReady() {
        if (startWithTtsOff()) {
            tts.setEnabled(false); // działa "po UI"
        }
        setupTtsToggleIfPresent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tts.isEnabled()) tts.speak(getSpeakText());
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }

    protected void setupTtsToggleIfPresent() {
        ImageButton btn = findViewById(R.id.btnTTS);
        if (btn == null) return;

        updateBtnIcon(btn);
        btn.setOnClickListener(v -> {
            boolean nowEnabled = tts.toggle();
            updateBtnIcon(btn);
            if (nowEnabled) tts.speak(getSpeakText());
            else tts.stop();
        });
    }

    private void updateBtnIcon(ImageButton btn) {
        boolean on = tts.isEnabled();
        btn.setImageResource(on ? R.drawable.ic_volume_on : R.drawable.ic_volume_off);
        btn.setContentDescription(getString(on ? R.string.tts_on : R.string.tts_off));
    }

    /**
     * Helper do scenariuszy/teorii: zbiera tekst do czytania z aktualnego layoutu:
     * - wszystkie widoczne TextView z niepustym tekstem
     * - z pominięciem Button (żeby nie czytać etykiet typu "Dalej", "Zakończ")
     */
    protected String collectSpeakableTextFromLayout() {
        View root = findViewById(android.R.id.content);
        List<String> parts = new ArrayList<>();
        collectTextRecursive(root, parts);

        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p == null) continue;
            String s = p.trim();
            if (s.isEmpty()) continue;

            if (sb.length() > 0) sb.append(". ");
            sb.append(s);
        }
        return sb.toString().trim();
    }

    private void collectTextRecursive(View v, List<String> out) {
        if (v == null) return;
        if (v.getVisibility() != View.VISIBLE) return;

        // Nie czytamy etykiet przycisków akcyjnych
        if (v instanceof Button) return;

        if (v instanceof TextView) {
            CharSequence cs = ((TextView) v).getText();
            if (cs != null) {
                String s = cs.toString().trim();
                if (!s.isEmpty()) out.add(s);
            }
            return;
        }

        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                collectTextRecursive(vg.getChildAt(i), out);
            }
        }
    }
}
