package com.example.smartsenior.ui.moduleMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module1Activity;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.CallFromAnUnknownMenuActivity;
import com.example.smartsenior.ui.moduleMenu.modules.fakenews.FakeNewsModuleActivity;

public class ModuleMenuActivity extends AppCompatActivity {

    private LinearLayout btnModule1, btnCallFromAnUnknown, btnShoppingOnline;
    private LinearLayout btnFakeNewsModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_menu);

        // Kafelek modułu "Bezpieczne wiadomości"
        btnModule1 = findViewById(R.id.btnSafeMessages);
        btnCallFromAnUnknown = findViewById(R.id.btnCallFromAnUnknown);
        btnShoppingOnline = findViewById(R.id.btnShoppingOnline);
        btnFakeNewsModule = findViewById(R.id.btnFakeNewsModule);

        btnModule1.setOnClickListener(v -> {
            Intent intent = new Intent(ModuleMenuActivity.this, Module1Activity.class);
            startActivity(intent);
        });

        btnCallFromAnUnknown.setOnClickListener(v -> {
            Intent intent = new Intent(ModuleMenuActivity.this, CallFromAnUnknownMenuActivity.class);
            startActivity(intent);
        });

        btnShoppingOnline.setOnClickListener(v -> {
            Intent intent = new Intent(ModuleMenuActivity.this, Module3Activity.class);
            startActivity(intent);
        });

        btnFakeNewsModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModuleMenuActivity.this, FakeNewsModuleActivity.class));
            }
        });

        // Powrót do tutorialu
        Button btnBackToTutorial = findViewById(R.id.button_back_to_tutorial);
        boolean fromTutorial = getIntent().getBooleanExtra("from_tutorial", false);

        if (fromTutorial) {
            btnBackToTutorial.setVisibility(View.VISIBLE);
            btnBackToTutorial.setOnClickListener(v -> {
                Intent intent = new Intent(ModuleMenuActivity.this,
                        com.example.smartsenior.ui.tutorial.TutorialActivity4.class);
                startActivity(intent);
                finish();
            });
        }
    }

    // ============= POWIĘKSZANIE TEKSTU (AUTOMATYCZNIE NA CAŁYM EKRANIE) =============
    @Override
    protected void onResume() {
        super.onResume();
        applyFontSize(findViewById(android.R.id.content));
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
}
