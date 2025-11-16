package com.example.smartsenior.ui.scenariosModule3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Bank1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_1);

        ImageView imgCall = findViewById(R.id.imgCall);
        Button btnEnd = findViewById(R.id.btnEnd);

        // Zielona słuchawka – start scenariusza (scena 2)
        imgCall.setOnClickListener(v -> {
            Intent intent = new Intent(Bank1Activity.this, Bank2Activity.class);
            startActivity(intent);
        });

        // "Koniec" – wyjście z modułu
        btnEnd.setOnClickListener(v -> finish());
    }
}
