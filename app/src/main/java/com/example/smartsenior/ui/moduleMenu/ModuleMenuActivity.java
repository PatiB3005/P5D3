package com.example.smartsenior.ui.moduleMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module1Activity;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.CallFromAnUnknownMenuActivity;

public class ModuleMenuActivity extends AppCompatActivity {

    private LinearLayout btnModule1, btnCallFromAnUnknown, btnModule3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_menu);

        // Kafelek modułu "Bezpieczne wiadomości"
        btnModule1 = findViewById(R.id.btnSafeMessages);
        btnCallFromAnUnknown = findViewById(R.id.btnCallFromAnUnknown);
        btnModule3 = findViewById(R.id.btnShoppingOnline);

        btnModule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleMenuActivity.this, Module1Activity.class);
                startActivity(intent);
            }
        });

        btnCallFromAnUnknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleMenuActivity.this, CallFromAnUnknownMenuActivity.class);
                startActivity(intent);
            }
        });

        btnModule3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleMenuActivity.this, Module3Activity.class);
                startActivity(intent);
            }
        });

        // Powrót do tutorialu
        Button btnBackToTutorial = findViewById(R.id.button_back_to_tutorial);
        boolean fromTutorial = getIntent().getBooleanExtra("from_tutorial", false);

        if (fromTutorial) {
            btnBackToTutorial.setVisibility(View.VISIBLE);
            btnBackToTutorial.setOnClickListener(v -> {
                Intent intent = new Intent(ModuleMenuActivity.this, com.example.smartsenior.ui.tutorial.TutorialActivity4.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
