package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class TheoryActivity extends BaseTTSActivity {

    private static final int LAST_SCREEN = 7;
    private int currentScreen = 1;

    private boolean firstScreenAlreadyShown = false;

    private ViewTreeObserver.OnScrollChangedListener customScrollbarListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
        firstScreenAlreadyShown = true;
    }

    private void showScreen(int screenNumber) {
        tts.stop();

        switch (screenNumber) {
            case 1: setContentView(R.layout.activity_safe_msg); break;
            case 2: setContentView(R.layout.activity_safe_msg2); break;
            case 3: setContentView(R.layout.activity_safe_msg3); break;
            case 4: setContentView(R.layout.activity_safe_msg4); break;
            case 5: setContentView(R.layout.activity_safe_msg5); break;
            case 6: setContentView(R.layout.activity_safe_msg6); break;
            case 7: setContentView(R.layout.activity_safe_msg7); break;
        }

        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        setupButtons();

        // ✅ zawsze start od góry
        ScrollView scroll = findViewById(R.id.scrollContent);
        if (scroll != null) {
            scroll.post(() -> scroll.scrollTo(0, 0));
        }

        // ✅ gruby scrollbar (jeśli jest w tym layoucie)
        setupCustomScrollbarIfPresent();

        if (firstScreenAlreadyShown) {
            getWindow().getDecorView().post(this::speakIfEnabled);
        }
    }

    private void goToModule1() {
        ProgressStore.markDone(this, ProgressKeys.M1_THEORY_DONE);

        Intent intent = new Intent(TheoryActivity.this, Module1Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void setupButtons() {
        View nextView = findViewById(R.id.btnNext);
        if (nextView != null) {

            if (nextView instanceof MaterialButton) {
                ((MaterialButton) nextView).setText(currentScreen == LAST_SCREEN ? "Koniec" : "Dalej");
            }

            nextView.setOnClickListener(v -> {
                tts.stop();
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

    private void setupCustomScrollbarIfPresent() {
        ScrollView scroll = findViewById(R.id.scrollContent);
        View thumb = findViewById(R.id.customScrollThumb);
        View bottomDivider = findViewById(R.id.bottomDivider);

        // topGuide to Guideline -> w kodzie go nie potrzebujesz, bo track liczymy z położeń widoków
        if (scroll == null || thumb == null || bottomDivider == null) return;

        // usuń poprzedni listener
        if (customScrollbarListener != null) {
            try {
                scroll.getViewTreeObserver().removeOnScrollChangedListener(customScrollbarListener);
            } catch (Exception ignored) {}
        }

        Runnable update = () -> {
            View child = scroll.getChildAt(0);
            if (child == null) return;

            int contentH = child.getHeight();
            int viewportH = scroll.getHeight();

            // brak scrolla => ukryj
            if (contentH <= viewportH) {
                thumb.setVisibility(View.GONE);
                thumb.setTranslationY(0f);
                return;
            } else {
                thumb.setVisibility(View.VISIBLE);
            }

            // ✅ wysokość toru: od góry ScrollView do dividera (nad przyciskami)
            int[] scrollLoc = new int[2];
            int[] dividerLoc = new int[2];
            scroll.getLocationOnScreen(scrollLoc);
            bottomDivider.getLocationOnScreen(dividerLoc);

            int trackTop = scrollLoc[1];
            int trackBottom = dividerLoc[1]; // divider jest linią nad przyciskami
            int trackHeight = Math.max(1, trackBottom - trackTop);

            // wysokość thumb proporcjonalna
            int computed = (int) (trackHeight * (viewportH / (float) contentH));
            int minPx = dpToPx(120);
            int thumbHeight = Math.max(minPx, computed);

            ViewGroup.LayoutParams lp = thumb.getLayoutParams();
            if (lp != null && lp.height != thumbHeight) {
                lp.height = thumbHeight;
                thumb.setLayoutParams(lp);
            }

            int scrollRange = Math.max(1, contentH - viewportH);
            float ratio = scroll.getScrollY() / (float) scrollRange;

            float maxY = trackHeight - thumbHeight;
            if (maxY < 0) maxY = 0;

            // ✅ przesunięcie w dół w torze
            thumb.setTranslationY(maxY * ratio);
        };

        customScrollbarListener = update::run;
        scroll.getViewTreeObserver().addOnScrollChangedListener(customScrollbarListener);

        scroll.post(update);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
