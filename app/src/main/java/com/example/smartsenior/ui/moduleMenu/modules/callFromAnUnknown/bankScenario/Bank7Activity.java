package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.bankScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Bank7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_7);

        TextView textAnswerA = findViewById(R.id.textAnswerA);
        ImageView imgHangup = findViewById(R.id.imgHangup);

        // A) „Dobrze, rozumiem.” -> końcowe pouczenie
        textAnswerA.setOnClickListener(v -> {
            Intent intent = new Intent(Bank7Activity.this, Bank8Activity.class);
            startActivity(intent);
        });

        imgHangup.setOnClickListener(v -> finish());
    }
}
