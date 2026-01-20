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

    private static final int LAST_SCREEN = 7;

    private int currentScreen = 1;

    // aktualny scroll + listener (żeby nie mnożyć listenerów)
    private ScrollView scroll;
    private View thumb;
    private View track;
    private ViewTreeObserver.OnScrollChangedListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
    }

    private void showScreen(int screenNumber) {
        switch (screenNumber) {
            case 1: setContentView(R.layout.activity_fake_news_theory_1); break;
            case 2: setContentView(R.layout.activity_fake_news_theory_2); break;
            case 3: setContentView(R.layout.activity_fake_news_theory_3); break;
            case 4: setContentView(R.layout.activity_fake_news_theory_4); break;
            case 5: setContentView(R.layout.activity_fake_news_theory_5); break;
            case 6: setContentView(R.layout.activity_fake_news_theory_6); break;
            case 7: setContentView(R.layout.activity_fake_news_theory_7); break;
        }

        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        setupButtons();
        setupCustomScrollThumbIfPresent(); // ✅ tak jak w TheoryActivity
    }

    private void setupCustomScrollThumbIfPresent() {
        // ✅ WAŻNE: ID jak w XML
        ScrollView newScroll = findViewById(R.id.scrollContent);
        View newThumb = findViewById(R.id.customScrollThumb);
        View newTrack = findViewById(R.id.customScrollTrack); // może być null na niektórych ekranach

        // jeśli nie ma scrolla/thumba na danym layoucie -> nic nie rób
        if (newScroll == null || newThumb == null) return;

        // usuń listener z poprzedniego scrolla (przed podmianą)
        if (scroll != null && scrollListener != null) {
            scroll.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
        }

        scroll = newScroll;
        thumb = newThumb;
        track = newTrack;

        // ustaw start: przewiń do góry i ustaw thumb
        scroll.post(() -> {
            scroll.scrollTo(0, 0);
            thumb.setTranslationY(0f);
            updateThumbPosition();
        });

        scrollListener = this::updateThumbPosition;
        scroll.getViewTreeObserver().addOnScrollChangedListener(scrollListener);

        // po pierwszym layoucie też zaktualizuj (na wypadek, gdyby wysokości były 0)
        scroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                scroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                updateThumbPosition();
            }
        });
    }

    private void updateThumbPosition() {
        if (scroll == null || thumb == null) return;
        if (scroll.getChildCount() == 0) return;

        View content = scroll.getChildAt(0);

        int scrollY = scroll.getScrollY();
        int contentHeight = content.getHeight();
        int viewportHeight = scroll.getHeight();
        int maxScroll = Math.max(1, contentHeight - viewportHeight);

        // ✅ Jeśli masz track w XML, to poruszaj się po jego wysokości (lepsze wizualnie).
        // Jeśli track == null, to fallback jak w TheoryActivity (po wysokości ScrollView).
        int travelBaseHeight = (track != null ? track.getHeight() : scroll.getHeight());
        float trackHeight = Math.max(0f, travelBaseHeight - thumb.getHeight());

        float progress = Math.min(1f, Math.max(0f, scrollY / (float) maxScroll));
        thumb.setTranslationY(trackHeight * progress);
    }

    private void setupButtons() {
        View next = findViewById(R.id.btnNext);
        if (next != null) {
            next.setOnClickListener(v -> {
                if (currentScreen < LAST_SCREEN) {
                    currentScreen++;
                    showScreen(currentScreen);
                } else {
                    ProgressStore.markDone(this, ProgressKeys.M2_THEORY_DONE);
                    startActivity(new Intent(this, FakeNewsMenuActivity.class));
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

    @Override
    protected void onDestroy() {
        if (scroll != null && scrollListener != null) {
            scroll.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
        }
        super.onDestroy();
    }
}
