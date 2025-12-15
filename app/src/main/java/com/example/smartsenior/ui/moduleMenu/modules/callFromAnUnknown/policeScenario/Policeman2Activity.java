package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_2);

        TextView answerA = findViewById(R.id.textAnswerA);
        TextView answerB = findViewById(R.id.textAnswerB);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman3Activity.class);
            startActivity(intent);
        });

        answerB.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman2aActivity.class);
            startActivity(intent);
        });

        declineCall.setOnClickListener(v -> {
            finish();
        });
    }
}
