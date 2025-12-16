package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_4);

        TextView answerA = findViewById(R.id.textAnswerA); // ← poprawione ID
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // ▶ A → SCENA 5
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman5Activity.class);
            startActivity(intent);
        });

        // ▶ Czerwona słuchawka → KONIEC
        declineCall.setOnClickListener(v -> {
            finish();
        });
    }
}
