package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Grandchild2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_2);

        TextView answerA = findViewById(R.id.textAnswerA);
        TextView answerB = findViewById(R.id.textAnswerB);
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A -> od razu słuchasz historii -> scena 3
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild2Activity.this,
                    Grandchild3Activity.class);
            startActivity(intent);
        });

        // B -> "Który wnuczek?" -> scena 2a
        answerB.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild2Activity.this,
                    Grandchild2aActivity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie scenariusza
        declineCall.setOnClickListener(v -> finish());
    }
}
