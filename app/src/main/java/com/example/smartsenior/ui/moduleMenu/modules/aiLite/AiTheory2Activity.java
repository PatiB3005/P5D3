package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class AiTheory2Activity extends BaseTTSActivity {

    private ViewTreeObserver.OnScrollChangedListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_theory2);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> {
            tts.stop();
            finish();
        });

        btnNext.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(AiTheory2Activity.this, AiTheory3Activity.class));
        });

        FontScaler.applyFontSize(this, findViewById(android.R.id.content));

        setupCustomScrollThumbIfPresent();
        setupImageZoom();
    }

    private void setupImageZoom() {
        ImageButton btnZoom = findViewById(R.id.btnZoom);
        if (btnZoom == null) return;

        btnZoom.setOnClickListener(v -> showImagePreview(R.drawable.kon));
    }

    private void showImagePreview(int imageResId) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_image_preview);

        ImageView preview = dialog.findViewById(R.id.previewImage);
        View close = dialog.findViewById(R.id.btnClose);

        if (preview != null) preview.setImageResource(imageResId);
        if (close != null) close.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void setupCustomScrollThumbIfPresent() {
        ScrollView scroll = findViewById(R.id.scrollArea);
        View thumb = findViewById(R.id.customScrollThumb);
        View track = findViewById(R.id.customScrollTrack);

        if (scroll == null || thumb == null) return;

        if (scrollListener != null) {
            scroll.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
            scrollListener = null;
        }

        scroll.post(() -> {
            scroll.scrollTo(0, 0);
            thumb.setTranslationY(0f);
            updateThumbPosition(scroll, thumb, track);
        });

        scrollListener = () -> updateThumbPosition(scroll, thumb, track);
        scroll.getViewTreeObserver().addOnScrollChangedListener(scrollListener);

        scroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                updateThumbPosition(scroll, thumb, track);
            }
        });
    }

    private void updateThumbPosition(ScrollView scroll, View thumb, View track) {
        if (scroll == null || thumb == null) return;
        if (scroll.getChildCount() == 0) return;

        View content = scroll.getChildAt(0);

        int scrollY = scroll.getScrollY();
        int maxScroll = Math.max(1, content.getHeight() - scroll.getHeight());

        int baseHeight = (track != null ? track.getHeight() : scroll.getHeight());
        float travel = Math.max(0f, baseHeight - thumb.getHeight());

        float progress = Math.min(1f, Math.max(0f, scrollY / (float) maxScroll));
        thumb.setTranslationY(travel * progress);
    }

    @Override
    protected void onDestroy() {
        ScrollView scroll = findViewById(R.id.scrollArea);
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
