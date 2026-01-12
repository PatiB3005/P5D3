package com.example.smartsenior.ui.moduleMenu.modules.fakenews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;

public class FakeNewsTheoryActivity extends AppCompatActivity {

    private int currentScreen = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
    }

    private void showScreen(int screenNumber) {
        switch (screenNumber) {
            case 1:
                setContentView(R.layout.activity_fake_news_theory_1);
                break;
            case 2:
                setContentView(R.layout.activity_fake_news_theory_2);
                break;
            case 3:
                setContentView(R.layout.activity_fake_news_theory_3);
                break;
            case 4:
                setContentView(R.layout.activity_fake_news_theory_4);
                break;
            case 5:
                setContentView(R.layout.activity_fake_news_theory_5);
                break;
            case 6:
                setContentView(R.layout.activity_fake_news_theory_6);
                break;
            case 7:
                setContentView(R.layout.activity_fake_news_theory_7);
                break;
        }
        setupButtons();
    }

    private void setupButtons() {
        View next = findViewById(R.id.btnNext);
        if (next != null) {
            next.setOnClickListener(v -> {
                if (currentScreen < 7) {
                    currentScreen++;
                    showScreen(currentScreen);
                } else {
                    ProgressStore.markDone(this, ProgressKeys.M2_THEORY_DONE);
                    Intent intent = new Intent(
                            FakeNewsTheoryActivity.this,
                            FakeNewsMenuActivity.class
                    );
                    startActivity(intent);
                    finish();
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
