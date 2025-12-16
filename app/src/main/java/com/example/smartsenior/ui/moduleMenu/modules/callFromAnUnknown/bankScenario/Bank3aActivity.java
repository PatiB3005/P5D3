package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Bank3aActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_3a);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A) dajesz się przekonać -> scena 4
        textAnswerA.setOnClickListener(v -> {
            Intent intent = new Intent(Bank3aActivity.this, Bank4Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie scenariusza
        declineCall.setOnClickListener(v -> finish());
    }
}
