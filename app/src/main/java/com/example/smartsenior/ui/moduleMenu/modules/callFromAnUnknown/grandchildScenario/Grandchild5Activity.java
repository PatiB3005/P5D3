package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Grandchild5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_5);

        TextView answerA = findViewById(R.id.textAnswerA);
        ImageView hangup = findViewById(R.id.imgHangup);

        // A -> przekazujesz pieniądze -> NEGATYWNE podsumowanie (scena 6)
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild5Activity.this,
                    Grandchild6Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie
        hangup.setOnClickListener(v -> finish());
    }
}
