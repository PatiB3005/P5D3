package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class AiTheoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailite_theory1);

        MaterialButton next = findViewById(R.id.btnNext);

        next.setOnClickListener(v -> {
            Intent intent = new Intent(this, AiTheory2Activity.class);
            startActivity(intent);
        });
    }
}
