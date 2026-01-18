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

public class ShoppingTheory8Activity extends BaseTTSActivity {

    private ScrollView scrollArea;
    private View customScrollThumb;
    private View bottomDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_8);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory8Activity.this, ShoppingTheory9Activity.class));
        });

        // zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // --- CUSTOM SCROLL BAR ---
        scrollArea = findViewById(R.id.scrollArea);
        customScrollThumb = findViewById(R.id.customScrollThumb);
        bottomDivider = findViewById(R.id.bottomDivider);

        setupCustomScrollbar();
    }

    private void setupCustomScrollbar() {
        if (scrollArea == null || customScrollThumb == null || bottomDivider == null) return;

        // Po narysowaniu widoków policz i ustaw startową pozycję thumb
        scrollArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                updateThumbPosition();
            }
        });

        scrollArea.setOnScrollChangeListener((View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) -> {
            updateThumbPosition();
        });
    }

    private void updateThumbPosition() {
        // content inside scroll
        View content = scrollArea.getChildAt(0);
        if (content == null) return;

        int contentHeight = content.getHeight();
        int viewportHeight = scrollArea.getHeight();
        int maxScroll = Math.max(1, contentHeight - viewportHeight);

        // obszar gdzie może poruszać się thumb: od góry ekranu do bottomDivider
        int trackTop = 0;
        int trackBottom = bottomDivider.getTop();
        int trackHeight = Math.max(1, trackBottom - trackTop);

        int thumbHeight = customScrollThumb.getHeight();
        int travel = Math.max(0, trackHeight - thumbHeight);

        float ratio = scrollArea.getScrollY() / (float) maxScroll;
        float y = trackTop + (travel * ratio);

        customScrollThumb.setY(y);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
