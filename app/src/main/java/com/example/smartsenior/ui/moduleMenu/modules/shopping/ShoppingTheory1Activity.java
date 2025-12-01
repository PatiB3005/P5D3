package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class ShoppingTheory1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_1);

        MaterialButton next = findViewById(R.id.btnNext);

        next.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingTheory2Activity.class);
            startActivity(intent);
        });
    }
}
