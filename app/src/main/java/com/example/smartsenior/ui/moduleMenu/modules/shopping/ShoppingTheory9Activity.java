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

public class ShoppingTheory9Activity extends BaseTTSActivity {

    private ScrollView scrollArea;
    private View customScrollThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_9);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory9Activity.this, ShoppingTheory10Activity.class));
        });

        // zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // CUSTOM SCROLLBAR
        setupCustomScrollbar();
    }

    private void setupCustomScrollbar() {
        scrollArea = findViewById(R.id.scrollArea);           // <-- musi istnieć w XML
        customScrollThumb = findViewById(R.id.customScrollThumb); // <-- musi istnieć w XML

        if (scrollArea == null || customScrollThumb == null) return;

        // start na górze
        scrollArea.post(() -> {
            scrollArea.scrollTo(0, 0);
            updateThumbPosition();
        });

        // aktualizacja podczas scrolla
        scrollArea.getViewTreeObserver().addOnScrollChangedListener(this::updateThumbPosition);

        // aktualizacja po ułożeniu widoku (np. po zmianie fontu)
        scrollArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updateThumbPosition();
                scrollArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void updateThumbPosition() {
        if (scrollArea == null || customScrollThumb == null) return;

        int scrollY = scrollArea.getScrollY();

        View content = scrollArea.getChildAt(0);
        if (content == null) return;

        int contentHeight = content.getHeight();
        int viewportHeight = scrollArea.getHeight();

        int maxScroll = Math.max(0, contentHeight - viewportHeight);

        // jeśli nie ma scrolla (krótka treść) -> pokaż pasek na górze
        if (maxScroll == 0) {
            customScrollThumb.setTranslationY(0f);
            return;
        }

        float progress = scrollY / (float) maxScroll;

        int trackHeight = scrollArea.getHeight(); // obszar od góry scrolla do nad przyciskami
        int thumbHeight = customScrollThumb.getHeight();

        float maxThumbTravel = Math.max(0, trackHeight - thumbHeight);

        customScrollThumb.setTranslationY(progress * maxThumbTravel);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
