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

public class ShoppingTheory2Activity extends BaseTTSActivity {

    private ViewTreeObserver.OnScrollChangedListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_2);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory2Activity.this, ShoppingTheory3Activity.class));
        });

        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // ✅ DODANE: obsługa custom scrollbara (jeśli jest w XML)
        setupCustomScrollThumbIfPresent();
    }

    private void setupCustomScrollThumbIfPresent() {
        ScrollView scroll = findScrollView();
        View thumb = findViewById(R.id.customScrollThumb);

        if (scroll == null || thumb == null) return;

        // usuń poprzedni listener (na wszelki wypadek)
        if (scrollListener != null) {
            scroll.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
            scrollListener = null;
        }

        // start na górze
        scroll.post(() -> {
            scroll.scrollTo(0, 0);
            thumb.setTranslationY(0f);
            updateThumbPosition(scroll, thumb);
        });

        scrollListener = () -> updateThumbPosition(scroll, thumb);
        scroll.getViewTreeObserver().addOnScrollChangedListener(scrollListener);
    }

    // ✅ Obsługuje oba warianty ID: scrollContent i scrollArea
    private ScrollView findScrollView() {
        View v = findViewById(R.id.scrollContent);
        if (v instanceof ScrollView) return (ScrollView) v;

        v = findViewById(R.id.scrollArea);
        if (v instanceof ScrollView) return (ScrollView) v;

        return null;
    }

    private void updateThumbPosition(ScrollView scroll, View thumb) {
        if (scroll.getChildCount() == 0) return;

        View content = scroll.getChildAt(0);

        int scrollY = scroll.getScrollY();
        int contentHeight = content.getHeight();
        int viewportHeight = scroll.getHeight();

        int maxScroll = Math.max(1, contentHeight - viewportHeight);

        float trackHeight = Math.max(0, scroll.getHeight() - thumb.getHeight());
        float progress = Math.min(1f, Math.max(0f, scrollY / (float) maxScroll));

        thumb.setTranslationY(trackHeight * progress);
    }

    @Override
    protected void onDestroy() {
        // sprzątanie listenera
        ScrollView scroll = findScrollView();
        if (scroll != null && scrollListener != null) {
            scroll.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
        }
        super.onDestroy();
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
