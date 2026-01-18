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

public class ShoppingTheory10Activity extends BaseTTSActivity {

    private ScrollView scrollArea;
    private View scrollThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_10);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory10Activity.this, ShoppingTheoryEndActivity.class));
        });

        // zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // --- CUSTOM SCROLL ---
        scrollArea = findViewById(R.id.scrollArea);
        scrollThumb = findViewById(R.id.customScrollThumb);

        setupCustomScrollbar();
    }

    private void setupCustomScrollbar() {
        if (scrollArea == null || scrollThumb == null) return;

        // Ustaw thumb na starcie (po layout)
        scrollArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                updateThumbPosition();
            }
        });

        // Aktualizuj thumb podczas scrollowania
        scrollArea.getViewTreeObserver().addOnScrollChangedListener(this::updateThumbPosition);
    }

    private void updateThumbPosition() {
        if (scrollArea == null || scrollThumb == null) return;

        View child = scrollArea.getChildAt(0);
        if (child == null) return;

        int scrollRange = Math.max(0, child.getHeight() - scrollArea.getHeight());
        int scrollY = scrollArea.getScrollY();

        // wysokość "toru" = wysokość scrollArea
        float trackHeight = scrollArea.getHeight();
        float thumbHeight = scrollThumb.getHeight();

        if (scrollRange == 0 || trackHeight <= thumbHeight) {
            // brak scrolla -> thumb na górze
            scrollThumb.setTranslationY(0f);
            return;
        }

        float progress = scrollY / (float) scrollRange; // 0..1
        float maxThumbTravel = trackHeight - thumbHeight;

        scrollThumb.setTranslationY(progress * maxThumbTravel);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
