package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Grandchild4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_4);

        TextView answerA = findViewById(R.id.textAnswerA); // ID z XML-a
        LinearLayout declineCall = findViewById(R.id.declineCall);

        // A -> przygotowujesz pieniądze -> scena 5
        answerA.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild4Activity.this,
                    Grandchild5Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – przerwanie
        declineCall.setOnClickListener(v -> finish());
    }
}
