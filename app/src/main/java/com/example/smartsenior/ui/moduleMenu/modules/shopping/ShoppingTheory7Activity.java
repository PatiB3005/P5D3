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

public class ShoppingTheory7Activity extends BaseTTSActivity {

    private ScrollView scrollArea;
    private View scrollThumb;
    private View topGuide;
    private View bottomDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_7);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory7Activity.this, ShoppingTheory8Activity.class));
        });

        // zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // --- CUSTOM SCROLLBAR ---
        scrollArea = findViewById(R.id.scrollArea);
        scrollThumb = findViewById(R.id.customScrollThumb);
        topGuide = findViewById(R.id.topGuide);
        bottomDivider = findViewById(R.id.bottomDivider);

        setupCustomScrollbar();
    }

    private void setupCustomScrollbar() {
        if (scrollArea == null || scrollThumb == null || topGuide == null || bottomDivider == null) {
            // jeśli w jakimś XML nie ma paska, nic nie robimy
            return;
        }

        // zawsze start na górze
        scrollArea.post(() -> {
            scrollArea.scrollTo(0, 0);
            scrollThumb.setTranslationY(0f);
            updateThumbPosition();
        });

        // aktualizuj w trakcie scrollowania
        scrollArea.getViewTreeObserver().addOnScrollChangedListener(this::updateThumbPosition);

        // po layoutowaniu policz poprawnie zakres
        scrollArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updateThumbPosition();
                scrollArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void updateThumbPosition() {
        if (scrollArea == null || scrollThumb == null) return;

        View content = scrollArea.getChildAt(0);
        if (content == null) return;

        int scrollY = scrollArea.getScrollY();
        int scrollRange = Math.max(1, content.getHeight() - scrollArea.getHeight());

        // tor: od topGuide do bottomDivider
        int[] topGuideLoc = new int[2];
        int[] bottomDividerLoc = new int[2];
        topGuide.getLocationOnScreen(topGuideLoc);
        bottomDivider.getLocationOnScreen(bottomDividerLoc);

        float trackTop = topGuideLoc[1];
        float trackBottom = bottomDividerLoc[1];
        float trackHeight = Math.max(1f, trackBottom - trackTop);

        float thumbHeight = scrollThumb.getHeight();
        float maxThumbTravel = Math.max(0f, trackHeight - thumbHeight);

        float progress = scrollY / (float) scrollRange; // 0..1
        float thumbY = progress * maxThumbTravel;

        scrollThumb.setTranslationY(thumbY);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
