package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario.Grandchild1Activity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario.Policeman1Activity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario.Bank1Activity;
import com.google.android.material.appbar.MaterialToolbar;

public class CallFromAnUnknownMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_from_unknown_menu);

        // Znajdź kafelki po ID
        LinearLayout btnTeoria = findViewById(R.id.btnTeoria);
        LinearLayout btnGrandChild = findViewById(R.id.btnGrandChild);
        LinearLayout btnPoliceman = findViewById(R.id.btnPoliceman);
        LinearLayout btnBankEmployee = findViewById(R.id.btnBankEmployee);

        // Obsługa kliknięć — otwieranie nowych ekranów
        btnTeoria.setOnClickListener(v -> {
            Intent intent = new Intent(this, TheoryActivity.class);
            startActivity(intent);
        });

        btnGrandChild.setOnClickListener(v -> {
            Intent intent = new Intent(this, Grandchild1Activity.class);
            startActivity(intent);
        });

        btnPoliceman.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman1Activity.class);
            startActivity(intent);
        });

        btnBankEmployee.setOnClickListener(v -> {
            Intent intent = new Intent(this, Bank1Activity.class);
            startActivity(intent);
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
