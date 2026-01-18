package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class ShoppingTheory6Activity extends BaseTTSActivity {

    private ScrollView scrollArea;
    private View customScrollThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_6);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory6Activity.this, ShoppingTheory7Activity.class));
        });

        // zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // --- CUSTOM SCROLL THUMB ---
        scrollArea = findViewById(R.id.scrollArea);
        customScrollThumb = findViewById(R.id.customScrollThumb);

        setupCustomScrollThumb();
    }

    private void setupCustomScrollThumb() {
        if (scrollArea == null || customScrollThumb == null) return;

        // Po narysowaniu layoutu ustawiamy pozycję startową "na górze"
        scrollArea.post(() -> updateScrollThumb());

        // Reakcja na scroll
        scrollArea.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            updateScrollThumb();
        });
    }

    private void updateScrollThumb() {
        if (scrollArea == null || customScrollThumb == null) return;

        View content = scrollArea.getChildAt(0);
        if (content == null) return;

        int scrollRange = content.getHeight() - scrollArea.getHeight();
        int trackHeight = scrollArea.getHeight() - customScrollThumb.getHeight();

        // Jeśli nie ma czego scrollować -> ukryj wskaźnik
        if (scrollRange <= 0 || trackHeight <= 0) {
            customScrollThumb.setVisibility(View.INVISIBLE);
            return;
        } else {
            customScrollThumb.setVisibility(View.VISIBLE);
        }

        float progress = scrollArea.getScrollY() / (float) scrollRange; // 0..1
        float thumbY = progress * trackHeight;

        customScrollThumb.setTranslationY(thumbY);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
