package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_3);

        TextView answerA = findViewById(R.id.textAnswerA);
        TextView answerB = findViewById(R.id.textAnswerB);
        ImageView hangup = findViewById(R.id.imgHangup);

        // ▶ A → SCENA 4 (u Ciebie: Policeman4Activity)
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman4Activity.class);
            startActivity(intent);
        });

        // ▶ B → KONIEC (scenariusz przerwany przez użytkownika)
        answerB.setOnClickListener(v -> {
            finish();
        });

        // ▶ Czerwona słuchawka → KONIEC
        hangup.setOnClickListener(v -> {
            finish();
        });
    }
}
