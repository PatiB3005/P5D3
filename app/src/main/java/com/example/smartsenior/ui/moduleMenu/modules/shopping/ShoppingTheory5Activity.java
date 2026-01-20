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

public class ShoppingTheory5Activity extends BaseTTSActivity {

    private ScrollView scrollArea;
    private View customScrollThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_5);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ShoppingTheory5Activity.this, ShoppingTheory6Activity.class));
        });

        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        // ✅ Custom scroll (niebieski pasek)
        scrollArea = findViewById(R.id.scrollArea);
        customScrollThumb = findViewById(R.id.customScrollThumb);
        setupCustomScrollbar();
    }

    private void setupCustomScrollbar() {
        if (scrollArea == null || customScrollThumb == null) return;

        // po ułożeniu widoków ustaw pozycję na górze i przelicz
        scrollArea.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        scrollArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        scrollArea.scrollTo(0, 0);
                        updateThumbPosition();

                        scrollArea.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                            updateThumbPosition();
                        });
                    }
                }
        );
    }

    private void updateThumbPosition() {
        if (scrollArea == null || customScrollThumb == null) return;

        View content = scrollArea.getChildAt(0);
        if (content == null) return;

        int contentHeight = content.getHeight();
        int scrollViewHeight = scrollArea.getHeight();

        int scrollRange = Math.max(1, contentHeight - scrollViewHeight);
        float progress = scrollArea.getScrollY() / (float) scrollRange; // 0..1

        // zakres ruchu paska (od góry scrolla do dołu scrolla)
        int thumbAreaHeight = scrollViewHeight - customScrollThumb.getHeight();
        thumbAreaHeight = Math.max(0, thumbAreaHeight);

        float translationY = progress * thumbAreaHeight;
        customScrollThumb.setTranslationY(translationY);
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
