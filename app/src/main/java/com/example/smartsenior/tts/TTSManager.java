package com.example.smartsenior.tts;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTSManager {

    // UJEDNOLICONE – te same co w SettingsActivity i BaseTTSActivity
    private static final String PREFS = "app_prefs";
    private static final String KEY_TTS_ENABLED = "tts_enabled";

    private static TTSManager instance;

    private final Context appContext;
    private TextToSpeech tts;
    private boolean ready = false;
    private String pendingText = null;

    private TTSManager(Context context) {
        this.appContext = context.getApplicationContext();

        // Opcjonalnie: max głośność multimediów (zostawiam jak masz)
        AudioManager audioManager = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        }

        this.tts = new TextToSpeech(appContext, status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale pl = Locale.forLanguageTag("pl-PL");
                int langRes = tts.setLanguage(pl);

                tts.setSpeechRate(0.95f);
                tts.setPitch(1.0f);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                    .build()
                    );
                }

                ready = (langRes != TextToSpeech.LANG_MISSING_DATA && langRes != TextToSpeech.LANG_NOT_SUPPORTED);

                if (!ready) {
                    Intent install = new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (install.resolveActivity(appContext.getPackageManager()) != null) {
                        appContext.startActivity(install);
                    }
                } else if (isEnabled() && pendingText != null && !pendingText.trim().isEmpty()) {
                    new Handler().postDelayed(() -> speakInternal(pendingText), 300);
                    pendingText = null;
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
        // DEFAULT TRUE – żeby lektor działał „od razu” dopóki user go nie wyłączy
        return appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getBoolean(KEY_TTS_ENABLED, true);
    }

    public void setEnabled(boolean enabled) {
        appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putBoolean(KEY_TTS_ENABLED, enabled).apply();
        if (!enabled) {
            pendingText = null;
            stop();
        }
    }

    public boolean toggle() {
        boolean newState = !isEnabled();
        setEnabled(newState);
        if (!newState) stop();
        return newState;
    }

    public void speak(String text) {
        if (!isEnabled() || text == null || text.trim().isEmpty()) return;

        text = text
                .replace("\n", " ")
                .replaceAll("\\bw(?=\\s+[A-ZĄĆĘŁŃÓŚŹŻ])", "w\u00A0")
                .replaceAll("\\bw(?=\\s+[a-ząćęłńóśźż])", "w\u00A0");

        if (!ready) {
            pendingText = text;
            return;
        }

        if (text.length() > 3800) {
            text = text.substring(0, 3800);
        }

        speakInternal(text);
    }

    private void speakInternal(String text) {
        new Thread(() -> {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "smart_senior_utterance");
                } else {
                    // starsze API
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            } catch (Exception e) {
                Log.e("TTS", "speak error", e);
            }
        }).start();
    }

    public void stop() {
        try { if (tts != null) tts.stop(); } catch (Exception ignored) {}
    }

    public void shutdown() {
        try {
            if (tts != null) {
                tts.stop();
                tts.shutdown();
            }
        } catch (Exception e) {
            Log.e("TTS", "shutdown error", e);
        }
    }
}
