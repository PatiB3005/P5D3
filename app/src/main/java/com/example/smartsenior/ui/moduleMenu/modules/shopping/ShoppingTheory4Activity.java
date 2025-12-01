package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class ShoppingTheory4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_4);

        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        // wraca do teorii 3
        btnBack.setOnClickListener(v -> finish());

        // przechodzi do teorii 5
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ShoppingTheory4Activity.this,
                    ShoppingTheory5Activity.class
            );
            startActivity(intent);
        });
    }
}
