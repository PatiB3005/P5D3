package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class TheoryActivity extends AppCompatActivity {

    private static final int LAST_SCREEN = 7;
    private int currentScreen = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
    }

    private void showScreen(int screenNumber) {
        switch (screenNumber) {
            case 1:
                setContentView(R.layout.activity_safe_msg);
                break;
            case 2:
                setContentView(R.layout.activity_safe_msg2);
                break;
            case 3:
                setContentView(R.layout.activity_safe_msg3);
                break;
            case 4:
                setContentView(R.layout.activity_safe_msg4);
                break;
            case 5:
                setContentView(R.layout.activity_safe_msg5);
                break;
            case 6:
                setContentView(R.layout.activity_safe_msg6);
                break;
            case 7:
                setContentView(R.layout.activity_safe_msg7);
                break;
        }

        setupButtons();
    }

    private void goToModule1() {
        Intent intent = new Intent(TheoryActivity.this, Module1Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void setupButtons() {

        View nextView = findViewById(R.id.btnNext);
        if (nextView != null) {

            // (opcjonalnie) zmień napis na ostatnim ekranie
            if (nextView instanceof MaterialButton) {
                ((MaterialButton) nextView).setText(currentScreen == LAST_SCREEN ? "Koniec" : "Dalej");
            }

            nextView.setOnClickListener(v -> {
                // ✅ jeśli jesteśmy na safe_msg7 -> wracamy do Module1
                if (currentScreen >= LAST_SCREEN) {
                    goToModule1();
                } else {
                    currentScreen++;
                    showScreen(currentScreen);
                }
            });
        }

        View back = findViewById(R.id.btnBack);
        if (back != null) {
            back.setOnClickListener(v -> {
                if (currentScreen > 1) {
                    currentScreen--;
                    showScreen(currentScreen);
                } else {
                    finish();
                }
            });
        }
    }
}
