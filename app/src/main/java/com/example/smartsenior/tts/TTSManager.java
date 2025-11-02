package com.example.smartsenior.tts;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTSManager {
    private static final String PREFS = "app_settings";
    private static final String KEY_TTS_ENABLED = "tts_enabled";
    private static TTSManager instance;

    private final Context appContext;
    private TextToSpeech tts;
    private boolean ready = false;
    private String pendingText = null; // ← zapamiętujemy tekst do pierwszego czytania

    private TTSManager(Context context) {
        this.appContext = context.getApplicationContext();
        this.tts = new TextToSpeech(appContext, status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale pl = Locale.forLanguageTag("pl-PL");
                int langRes = tts.setLanguage(pl);
                tts.setSpeechRate(0.95f);
                tts.setPitch(1.0f);
                ready = (langRes != TextToSpeech.LANG_MISSING_DATA && langRes != TextToSpeech.LANG_NOT_SUPPORTED);

                if (!ready) {
                    Intent install = new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (install.resolveActivity(appContext.getPackageManager()) != null) {
                        appContext.startActivity(install);
                    }
                } else {
                    // jeśli lektor jest włączony i mamy coś w buforze – powiedz to teraz
                    if (isEnabled() && pendingText != null && !pendingText.trim().isEmpty()) {
                        speakInternal(pendingText);
                        pendingText = null; // czyścimy bufor
                    }
                }
            } else {
                ready = false;
            }
        });
    }

    public static synchronized TTSManager get(Context context) {
        if (instance == null) instance = new TTSManager(context);
        return instance;
    }

    public boolean isEnabled() {
        // domyślnie WYŁĄCZONY
        return appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getBoolean(KEY_TTS_ENABLED, false);
    }

    public void setEnabled(boolean enabled) {
        appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putBoolean(KEY_TTS_ENABLED, enabled).apply();
        if (!enabled) {
            pendingText = null; // wyłączenie = zerujemy planowaną wypowiedź
            stop();
        }
    }

    public boolean toggle() {
        boolean newState = !isEnabled();
        setEnabled(newState);
        if (!newState) stop();
        return newState;
    }

    /** Publiczne API. Jeśli TTS nie gotowy – zapamiętujemy tekst i odtworzymy po onInit(). */
    public void speak(String text) {
        if (!isEnabled() || text == null || text.trim().isEmpty()) return;
        if (!ready) {                 // ← klucz: pierwsze kliknięcie już coś „zrobi”
            pendingText = text;
            return;
        }
        speakInternal(text);
    }

    private void speakInternal(String text) {
        try {
            tts.stop();
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "smart_senior_utterance");
        } catch (Exception e) {
            Log.e("TTS", "speak error", e);
        }
    }

    public void stop() {
        try { if (tts != null) tts.stop(); } catch (Exception ignored) {}
    }

    public void shutdown() {
        try {
            if (tts != null) { tts.stop(); tts.shutdown(); }
        } catch (Exception e) { Log.e("TTS", "shutdown error", e); }
    }
}
