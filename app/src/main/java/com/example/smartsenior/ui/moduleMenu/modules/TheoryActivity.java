package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
public class TheoryActivity extends AppCompatActivity {

    private int currentScreen = 1; // zaczynamy od ekranu 1

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
            case 8:
                setContentView(R.layout.activity_safe_msg8);
                break;
        }

        setupButtons();
    }

    private void setupButtons() {

        // next
        View next = findViewById(R.id.btnNext);
        if (next != null) {
            next.setOnClickListener(v -> {
                if (currentScreen < 8) {
                    currentScreen++;
                    showScreen(currentScreen);
                } else {
                    // 🔥 PO 8 EKRANIE – POWRÓT DO MENU
                    Intent intent = new Intent(TheoryActivity.this, Module1Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // back
        View back = findViewById(R.id.btnBack);
        if (back != null) {
            back.setOnClickListener(v -> {
                if (currentScreen > 1) {
                    currentScreen--;
                    showScreen(currentScreen);
                } else {
                    finish(); // wyjście z modułu
                }
            });
        }
    }
}
