package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class ShoppingTheory2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_2);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        // WSTECZ — wraca do Teorii 1
        btnBack.setOnClickListener(v -> finish());

        // DALEJ — przechodzi do Teorii 3
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ShoppingTheory2Activity.this,
                    ShoppingTheory3Activity.class
            );
            startActivity(intent);
        });
    }
}
