package com.example.smartsenior.ui.moduleMenu.modules.aiLite.theory;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.increaseFont.FontScaler;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;

public class AiTheory2Activity extends BaseTTSActivity {

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

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
