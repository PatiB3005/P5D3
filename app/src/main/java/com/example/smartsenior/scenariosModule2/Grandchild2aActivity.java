package com.example.smartsenior.scenariosModule2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Grandchild2aActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_2a);

        TextView answerA = findViewById(R.id.textAnswerA);
        ImageView hangup = findViewById(R.id.imgHangup);

        // A -> "Dobrze, mów szybko..." -> scena 3
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild2aActivity.this,
                    Grandchild3Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie
        hangup.setOnClickListener(v -> finish());
    }
}
