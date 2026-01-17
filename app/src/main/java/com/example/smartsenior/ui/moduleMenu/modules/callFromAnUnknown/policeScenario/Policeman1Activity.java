package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;


public class Policeman1Activity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_1);

        LinearLayout answerCall = findViewById(R.id.answerCall);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // Start dźwięku i wibracji po wejściu na ekran
        startRingtoneSafely();
        startVibrationSafely();

        // Zielona słuchawka -> przejście do sceny 2
        answerCall.setOnClickListener(v -> {
            stopRingtone();
            stopVibration();

            Intent intent = new Intent(Policeman1Activity.this, Policeman2Activity.class);
            startActivity(intent);
        });

        // "Koniec" -> zatrzymanie wszystkiego i wyjście
        declineCall.setOnClickListener(v -> {
            stopRingtone();
            stopVibration();

            Intent intent = new Intent(Policeman1Activity.this, Policeman6bActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    // ---------------- DŹWIĘK ----------------
    private void startRingtoneSafely() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.suspicious_ring);
            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRingtone() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- WIBRACJE ----------------
    private void startVibrationSafely() {
        try {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator == null || !vibrator.hasVibrator()) return;

            long[] pattern = {0, 600, 400, 600, 400};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createWaveform(pattern, 0);
                vibrator.vibrate(effect);
            } else {
                vibrator.vibrate(pattern, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopVibration() {
        try {
            if (vibrator != null) vibrator.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Bezpieczeństwo – zatrzymujemy dźwięk/wibracje, gdy użytkownik opuści ekran
    @Override
    protected void onPause() {
        super.onPause();
        stopRingtone();
        stopVibration();
    }
}

