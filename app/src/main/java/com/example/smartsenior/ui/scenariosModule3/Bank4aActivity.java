package com.example.smartsenior.ui.scenariosModule3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Bank4aActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_4a);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        TextView textAnswerB = findViewById(R.id.textAnswerB);
        ImageView imgHangup = findViewById(R.id.imgHangup);

        // A) w końcu podajesz kod -> zła ścieżka -> scena 5
        textAnswerA.setOnClickListener(v -> {
            Intent intent = new Intent(Bank4aActivity.this, Bank5Activity.class);
            startActivity(intent);
        });

        // B) dalej nie ufasz -> kończymy scenariusz pouczeniem
        textAnswerB.setOnClickListener(v -> {
            Intent intent = new Intent(Bank4aActivity.this, Bank8Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie scenariusza
        imgHangup.setOnClickListener(v -> finish());
    }
}
