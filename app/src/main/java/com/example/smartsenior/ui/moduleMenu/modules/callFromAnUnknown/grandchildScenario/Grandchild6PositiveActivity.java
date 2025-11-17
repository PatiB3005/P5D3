package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.grandchildScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Grandchild6PositiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_6_positive);

        Button next = findViewById(R.id.btnNext);
        ImageView hangup = findViewById(R.id.imgHangup);

        // Dalej -> to samo POUCZENIE co po negatywnym wyniku
        next.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild6PositiveActivity.this,
                    Grandchild7Activity.class);
            startActivity(intent);
        });

        hangup.setOnClickListener(v -> finish());
    }
}
