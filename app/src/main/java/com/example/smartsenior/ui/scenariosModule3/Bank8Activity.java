package com.example.smartsenior.ui.scenariosModule3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;

public class Bank8Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_8);

        Button btnFinish = findViewById(R.id.btnFinish);

        // Zakończ – wróć do menu modułów
        btnFinish.setOnClickListener(v -> {
            Intent intent = new Intent(Bank8Activity.this, ModuleMenuActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
