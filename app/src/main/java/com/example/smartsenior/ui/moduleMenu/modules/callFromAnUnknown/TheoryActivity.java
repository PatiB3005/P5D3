package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;

public class TheoryActivity extends AppCompatActivity {

    private int currentScreen = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
    }

    private void showScreen(int screenNumber) {
        switch (screenNumber) {
            case 1:
                setContentView(R.layout.activity_call_from_unknown_1);
                break;
            case 2:
                setContentView(R.layout.activity_call_from_unknown_2);
                break;
            case 3:
                setContentView(R.layout.activity_call_from_unknown_3);
                break;
            case 4:
                setContentView(R.layout.activity_call_from_unknown_4);
                break;
            case 5:
                setContentView(R.layout.activity_call_from_unknown_5);
                break;
            case 6:
                setContentView(R.layout.activity_call_from_unknown_6);
                break;
            case 7:
                setContentView(R.layout.activity_call_from_unknown_7);
                break;
            case 8:
                setContentView(R.layout.activity_call_from_unknown_8);
                break;
        }
        setupButtons();
    }

    private void setupButtons() {
        View next = findViewById(R.id.btnNext);
        if (next != null) {
            next.setOnClickListener(v -> {
                if (currentScreen < 8) {
                    currentScreen++;
                    showScreen(currentScreen);
                } else {
                    ProgressStore.markDone(this, ProgressKeys.M2_THEORY_DONE);
                    Intent intent = new Intent(
                            TheoryActivity.this,
                            CallFromAnUnknownMenuActivity.class
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
