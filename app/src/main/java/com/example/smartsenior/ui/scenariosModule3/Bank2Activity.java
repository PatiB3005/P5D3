package com.example.smartsenior.ui.scenariosModule3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Bank2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_2);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        ImageView imgHangup = findViewById(R.id.imgHangup);

        // A -> kontynuujesz rozmowę -> scena 3
        textAnswerA.setOnClickListener(v -> {
            Intent intent = new Intent(Bank2Activity.this, Bank3Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie scenariusza
        imgHangup.setOnClickListener(v -> finish());
    }
}
