package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Bank3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_3);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        TextView textAnswerB = findViewById(R.id.textAnswerB);
        ImageView imgHangup = findViewById(R.id.imgHangup);

        // A) wierzysz od razu -> dalej w oszustwo -> scena 4
        textAnswerA.setOnClickListener(v -> {
            Intent intent = new Intent(Bank3Activity.this, Bank4Activity.class);
            startActivity(intent);
        });

        // B) pytasz o wiarygodność -> scena 3a
        textAnswerB.setOnClickListener(v -> {
            Intent intent = new Intent(Bank3Activity.this, Bank3aActivity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie scenariusza
        imgHangup.setOnClickListener(v -> finish());
    }
}
