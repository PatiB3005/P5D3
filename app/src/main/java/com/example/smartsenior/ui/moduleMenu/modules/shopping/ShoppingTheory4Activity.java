package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class ShoppingTheory4Activity extends BaseTTSActivity {

    private ScrollView scrollView;
    private View scrollThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_4);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory4Activity.this, ShoppingTheory5Activity.class));
        });

        // zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // ✅ custom scrollbar (działa tylko jeśli w XML istnieją te id)
        setupCustomScrollbar();
    }

    private void setupCustomScrollbar() {
        // U Ciebie w XML ScrollView ma id: scrollArea
        scrollView = findViewById(R.id.scrollArea);

        // U Ciebie w XML pasek ma id: customScrollThumb
        scrollThumb = findViewById(R.id.customScrollThumb);

        if (scrollView == null || scrollThumb == null) return;

        // Zrób update po ułożeniu widoku (żeby znać wysokości)
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        updateThumb();
                    }
                }
        );

        scrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> updateThumb());
    }

    private void updateThumb() {
        if (scrollView == null || scrollThumb == null) return;

        View content = scrollView.getChildAt(0);
        if (content == null) return;

        int viewport = scrollView.getHeight();
        int contentH = content.getHeight();
        int maxScroll = Math.max(0, contentH - viewport);

        // jeśli nie ma co scrollować -> ukryj pasek
        if (maxScroll <= 0) {
            scrollThumb.setVisibility(View.GONE);
            return;
        } else {
            scrollThumb.setVisibility(View.VISIBLE);
        }

        // wysokość tracka = od góry scrolla do separatora (czyli wysokość scrollView)
        float trackH = viewport;

        // wysokość "thumb" proporcjonalna do widocznej części
        float thumbH = trackH * ((float) viewport / (float) contentH);

        // ustaw minimalną wysokość, żeby był widoczny
        float minThumbPx = dpToPx(60);
        if (thumbH < minThumbPx) thumbH = minThumbPx;

        // zaktualizuj layout params paska
        scrollThumb.getLayoutParams().height = (int) thumbH;
        scrollThumb.requestLayout();

        // pozycja paska (0..trackH-thumbH)
        float progress = (float) scrollView.getScrollY() / (float) maxScroll;
        float maxTranslate = trackH - thumbH;
        if (maxTranslate < 0) maxTranslate = 0;

        scrollThumb.setTranslationY(progress * maxTranslate);
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
