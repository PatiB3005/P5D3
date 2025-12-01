package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class ShoppingTheory6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_6);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(v -> finish());

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ShoppingTheory6Activity.this,
                    ShoppingTheory7Activity.class   // <-- kolejna strona teorii
            );
            startActivity(intent);
        });

    }
}
