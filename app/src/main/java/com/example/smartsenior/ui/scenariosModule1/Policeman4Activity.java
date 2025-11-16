package com.example.smartsenior.ui.scenariosModule1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_4);

        TextView answerA = findViewById(R.id.answerA); // ← poprawione ID
        ImageView hangup = findViewById(R.id.hangup);  // ← poprawione ID

        // ▶ A → SCENA 5
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman5Activity.class);
            startActivity(intent);
        });

        // ▶ Czerwona słuchawka → KONIEC
        hangup.setOnClickListener(v -> {
            finish();
        });
    }
}
