package com.example.smartsenior.ui.moduleMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.example.smartsenior.ui.moduleMenu.modules.Module1Activity;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.CallFromAnUnknownMenuActivity;
import com.example.smartsenior.ui.moduleMenu.modules.fakenews.FakeNewsMenuActivity;
import com.example.smartsenior.ui.moduleMenu.modules.aiLite.AiActivity;

public class ModuleMenuActivity extends BaseTTSActivity {

    private LinearLayout btnModule1, btnCallFromAnUnknown, btnShoppingOnline, btnAiLite;
    private LinearLayout btnFakeNewsModule;

    private ProgressBar progressModule1, progressModule2, progressModule3, progressFakeNews, progressAiLite;
    private TextView labelModule1, labelModule2, labelModule3Number, labelFakeNews, labelAiLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_menu);

        // modules
        btnModule1 = findViewById(R.id.btnSafeMessages);
        btnCallFromAnUnknown = findViewById(R.id.btnCallFromAnUnknown);
        btnShoppingOnline = findViewById(R.id.btnShoppingOnline);
        btnFakeNewsModule = findViewById(R.id.btnFakeNewsModule);
        btnAiLite = findViewById(R.id.btnAiLite);

        // Progress Module
        progressModule1 = findViewById(R.id.progressModule1);
        progressModule2 = findViewById(R.id.progressModule2);
        progressModule3 = findViewById(R.id.progressModule3);
        progressFakeNews = findViewById(R.id.progressFakeNews);
        progressAiLite = findViewById(R.id.progressAiLite);

        // labels
        labelModule1 = findViewById(R.id.textModule1ProgressLabel);
        labelModule2 = findViewById(R.id.textModule2ProgressLabel);
        labelModule3Number = findViewById(R.id.textModule3ProgressNumber);
        labelFakeNews = findViewById(R.id.textFakeNewsProgressLabel);
        labelAiLite = findViewById(R.id.textAiProgressLabel);

        btnModule1.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ModuleMenuActivity.this, Module1Activity.class));
        });

        btnCallFromAnUnknown.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ModuleMenuActivity.this, CallFromAnUnknownMenuActivity.class));
        });

        btnShoppingOnline.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ModuleMenuActivity.this, Module3Activity.class));
        });

        btnFakeNewsModule.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ModuleMenuActivity.this, FakeNewsMenuActivity.class));
        });

        btnAiLite.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(ModuleMenuActivity.this, AiActivity.class));
        });

        // backButton
        ImageButton btnBackToMain = findViewById(R.id.btnBackToMain);
        if (btnBackToMain != null) {
            btnBackToMain.setOnClickListener(v -> {
                tts.stop();
                Intent intent = new Intent(ModuleMenuActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        Button btnBackToTutorial = findViewById(R.id.button_back_to_tutorial);
        boolean fromTutorial = getIntent().getBooleanExtra("from_tutorial", false);

        if (fromTutorial) {
            btnBackToTutorial.setVisibility(View.VISIBLE);
            btnBackToTutorial.setOnClickListener(v -> {
                tts.stop();
                Intent intent = new Intent(ModuleMenuActivity.this,
                        com.example.smartsenior.ui.tutorial.TutorialActivity4.class);
                startActivity(intent);
                finish();
            });
        }
    }

    @Override
    protected void onResume() {
        applyFontSize(findViewById(android.R.id.content));
        refreshProgressUI();
        super.onResume();
    }

    private void refreshProgressUI() {
        int p1 = ProgressStore.getPercent(this, ProgressKeys.MODULE1_PARTS);
        int p2 = ProgressStore.getPercent(this, ProgressKeys.MODULE2_PARTS);
        int p3 = ProgressStore.getPercent(this, ProgressKeys.MODULE3_PARTS);
        int pFN = ProgressStore.getPercent(this, ProgressKeys.FAKENEWS_PARTS);
        int pAI = ProgressStore.getPercent(this, ProgressKeys.AI_PARTS);

        if (progressModule1 != null) progressModule1.setProgress(p1);
        if (progressModule2 != null) progressModule2.setProgress(p2);
        if (progressModule3 != null) progressModule3.setProgress(p3);
        if (progressFakeNews != null) progressFakeNews.setProgress(pFN);
        if (progressAiLite != null) progressAiLite.setProgress(pAI);

        if (labelModule1 != null) labelModule1.setText("Postęp: " + p1 + "%");
        if (labelModule2 != null) labelModule2.setText("Postęp: " + p2 + "%");
        if (labelModule3Number != null) labelModule3Number.setText("Postęp: " + p3 + "%");
        if (labelFakeNews != null) labelFakeNews.setText("Postęp: " + pFN + "%");
        if (labelAiLite != null) labelAiLite.setText("Postęp: " + pAI + "%");
    }

    private void applyFontSize(View root) {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isLarge = prefs.getBoolean("large_font", false);

        float titleSize = isLarge ? 28f : 22f;
        float normalSize = isLarge ? 20f : 16f;
        float smallSize = isLarge ? 18f : 14f;

        scaleTextRecursively(root, titleSize, normalSize, smallSize);
    }

    private void scaleTextRecursively(View view, float titleSize, float normalSize, float smallSize) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;

            float current = tv.getTextSize() / getResources().getDisplayMetrics().scaledDensity;

            if (current >= 26) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
            } else if (current >= 16) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, normalSize);
            } else {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, smallSize);
            }
        }

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                scaleTextRecursively(group.getChildAt(i), titleSize, normalSize, smallSize);
            }
        }
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
