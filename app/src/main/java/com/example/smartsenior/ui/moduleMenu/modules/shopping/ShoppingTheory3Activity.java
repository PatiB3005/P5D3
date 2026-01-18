package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class ShoppingTheory3Activity extends BaseTTSActivity {

    private ScrollView scrollView;
    private View customScrollThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_3);

        // Znajdź widok ScrollView i customScrollThumb
        scrollView = findViewById(R.id.scrollArea);
        customScrollThumb = findViewById(R.id.customScrollThumb);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory3Activity.this, ShoppingTheory4Activity.class));
        });

        // Zastosuj duży/mały font na aktualnym layoucie
        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // Dodaj logikę do dynamicznego przewijania scrolla
        addScrollListener();
    }

    private void addScrollListener() {
        // Dodaj nasłuchiwanie dla scrolla
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY();
            // Oblicz procent przewinięcia
            int maxScrollY = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
            float scrollPercentage = (float) scrollY / maxScrollY;

            // Zaktualizuj pozycję customScrollThumb
            int thumbHeight = customScrollThumb.getHeight();
            int thumbPosition = (int) (scrollPercentage * (scrollView.getHeight() - thumbHeight));

            customScrollThumb.setTranslationY(thumbPosition);
        });
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
