package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Grandchild6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_6);

        Button next = findViewById(R.id.btnNext);
        ImageView hangup = findViewById(R.id.imgHangup);

        // Dalej -> POUCZENIE (Grandchild7Activity)
        next.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild6Activity.this,
                    Grandchild7Activity.class);
            startActivity(intent);
        });

        // Czerwona słuchawka – zamyka ekran
        hangup.setOnClickListener(v -> finish());
    }
}
