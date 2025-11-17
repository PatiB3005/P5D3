package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Bank4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_4);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        TextView textAnswerB = findViewById(R.id.textAnswerB);
        ImageView imgHangup = findViewById(R.id.imgHangup);

        // A) podajesz kod -> wchodzisz głębiej w oszustwo -> scena 5
        textAnswerA.setOnClickListener(v -> {
            Intent intent = new Intent(Bank4Activity.this, Bank5Activity.class);
            startActivity(intent);
        });

        // B) nie ufasz -> oszust próbuje dalej naciskać -> scena 4a
        textAnswerB.setOnClickListener(v -> {
            Intent intent = new Intent(Bank4Activity.this, Bank4aActivity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie scenariusza
        imgHangup.setOnClickListener(v -> finish());
    }
}
