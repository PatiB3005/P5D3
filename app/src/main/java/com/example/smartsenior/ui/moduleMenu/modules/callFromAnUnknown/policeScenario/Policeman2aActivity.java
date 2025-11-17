package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman2aActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_2a);

        TextView answerA = findViewById(R.id.textAnswerA);
        ImageView hangup = findViewById(R.id.imgHangup);

        // ▶ A → SCENA 4 (u Ciebie: Policeman3Activity)
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman3Activity.class);
            startActivity(intent);
        });

        // ▶ Czerwona słuchawka → koniec scenariusza
        hangup.setOnClickListener(v -> {
            finish();
        });
    }
}
