package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Grandchild3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_3);

        TextView answerA = findViewById(R.id.textAnswerA);
        TextView answerB = findViewById(R.id.textAnswerB);
        ImageView hangup = findViewById(R.id.imgHangup);

        // A -> idziesz za "wnuczkiem" -> dalszy scam (scena 4)
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild3Activity.this,
                    Grandchild4Activity.class);
            startActivity(intent);
        });

        // B -> dzwonisz do rodziny/policji -> POZYTYWNE podsumowanie
        answerB.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild3Activity.this,
                    Grandchild6PositiveActivity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – zakończenie bez podsumowania
        hangup.setOnClickListener(v -> finish());
    }
}
