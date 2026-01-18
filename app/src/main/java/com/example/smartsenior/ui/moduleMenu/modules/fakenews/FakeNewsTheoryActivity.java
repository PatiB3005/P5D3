package com.example.smartsenior.ui.moduleMenu.modules.fakenews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;

public class FakeNewsTheoryActivity extends AppCompatActivity {

    private int currentScreen = 1;

    private ScrollView scrollArea;
    private View scrollThumb;

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

        // zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // --- CUSTOM SCROLL (po KAŻDYM setContentView) ---
        bindCustomScrollForCurrentLayout();

        setupButtons();
    }

    private void bindCustomScrollForCurrentLayout() {
        // Jeśli masz inne ID w XML, zmień tutaj:
        scrollArea = findViewById(R.id.scrollArea);
        scrollThumb = findViewById(R.id.customScrollThumb);

        if (scrollArea == null || scrollThumb == null) return;

        // 1) ustaw thumb na start po layout
        scrollArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                updateThumbPosition();
            }
        });

        // 2) aktualizuj podczas scrollowania
        scrollArea.getViewTreeObserver().addOnScrollChangedListener(this::updateThumbPosition);

        // 3) dodatkowo: jak przełączasz ekrany, ScrollView czasem pamięta pozycję — ustaw na górę
        scrollArea.post(() -> {
            scrollArea.scrollTo(0, 0);
            updateThumbPosition();
        });
    }

    private void updateThumbPosition() {
        if (scrollArea == null || scrollThumb == null) return;

        View child = scrollArea.getChildAt(0);
        if (child == null) return;

        int scrollRange = Math.max(0, child.getHeight() - scrollArea.getHeight());
        int scrollY = scrollArea.getScrollY();

        float trackHeight = scrollArea.getHeight();
        float thumbHeight = scrollThumb.getHeight();

        if (scrollRange == 0 || trackHeight <= thumbHeight) {
            scrollThumb.setTranslationY(0f);
            return;
        }

        float progress = scrollY / (float) scrollRange; // 0..1
        float maxThumbTravel = trackHeight - thumbHeight;

        scrollThumb.setTranslationY(progress * maxThumbTravel);
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
                    Intent intent = new Intent(FakeNewsTheoryActivity.this, FakeNewsMenuActivity.class);
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
