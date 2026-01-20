package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;

public class TheoryActivity extends BaseTTSActivity {

    private static final int LAST_SCREEN = 8;

    private int currentScreen = 1;
    private boolean hasResumed = false;

    // żeby nie dodawać wielu listenerów
    private ViewTreeObserver.OnScrollChangedListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasResumed = true;
    }

    private void showScreen(int screenNumber) {
        switch (screenNumber) {
            case 1: setContentView(R.layout.activity_call_from_unknown_1); break;
            case 2: setContentView(R.layout.activity_call_from_unknown_2); break;
            case 3: setContentView(R.layout.activity_call_from_unknown_3); break;
            case 4: setContentView(R.layout.activity_call_from_unknown_4); break;
            case 5: setContentView(R.layout.activity_call_from_unknown_5); break;
            case 6: setContentView(R.layout.activity_call_from_unknown_6); break;
            case 7: setContentView(R.layout.activity_call_from_unknown_7); break;
            case 8: setContentView(R.layout.activity_call_from_unknown_8); break;
        }

        // Font
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        setupButtons();

        // ✅ tutaj podpinamy thumb do scrolla (jeśli jest w layoucie)
        setupCustomScrollThumbIfPresent();
    }

    private void setupCustomScrollThumbIfPresent() {
        ScrollView scroll = findViewById(R.id.scrollContent);
        View thumb = findViewById(R.id.customScrollThumb);

        // Jeśli na danym ekranie nie ma custom scrolla -> nic nie robimy
        if (scroll == null || thumb == null) return;

        // usuń poprzedni listener (gdy przechodzisz między screenami)
        if (scrollListener != null) {
            scroll.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
            scrollListener = null;
        }

        // ustaw na start: przewiń do góry i thumb na górze
        scroll.post(() -> {
            scroll.scrollTo(0, 0);
            thumb.setTranslationY(0f);
            updateThumbPosition(scroll, thumb);
        });

        // listener przewijania
        scrollListener = () -> updateThumbPosition(scroll, thumb);
        scroll.getViewTreeObserver().addOnScrollChangedListener(scrollListener);
    }

    private void updateThumbPosition(ScrollView scroll, View thumb) {
        if (scroll.getChildCount() == 0) return;

        View content = scroll.getChildAt(0);

        int scrollY = scroll.getScrollY();
        int contentHeight = content.getHeight();
        int viewportHeight = scroll.getHeight();

        int maxScroll = Math.max(1, contentHeight - viewportHeight); // unikamy dzielenia przez 0

        // tor, po którym porusza się thumb (od 0 do trackHeight)
        // trackHeight = (wysokość ScrollView) - (wysokość thumb)
        float trackHeight = Math.max(0, scroll.getHeight() - thumb.getHeight());

        float progress = Math.min(1f, Math.max(0f, scrollY / (float) maxScroll));
        float thumbY = trackHeight * progress;

        thumb.setTranslationY(thumbY);
    }

    private void setupButtons() {
        View next = findViewById(R.id.btnNext);
        if (next != null) {
            next.setOnClickListener(v -> {
                tts.stop();

                if (currentScreen < LAST_SCREEN) {
                    currentScreen++;
                    showScreen(currentScreen);
                } else {
                    ProgressStore.markDone(this, ProgressKeys.M2_THEORY_DONE);
                    Intent intent = new Intent(TheoryActivity.this, CallFromAnUnknownMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        View back = findViewById(R.id.btnBack);
        if (back != null) {
            back.setOnClickListener(v -> {
                tts.stop();

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
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }

    @Override
    protected void onDestroy() {
        // porządek: usuń listener
        ScrollView scroll = findViewById(R.id.scrollContent);
        if (scroll != null && scrollListener != null) {
            scroll.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
        }
        super.onDestroy();
    }
}
