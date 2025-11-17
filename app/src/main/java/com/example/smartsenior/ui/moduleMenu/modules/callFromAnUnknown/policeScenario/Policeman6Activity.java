package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_6);

        Button next = findViewById(R.id.btnNext);
        ImageView hangup = findViewById(R.id.imgHangup);

        // ▶ „Dalej” → ekran POUCZENIE (Policeman7Activity)
        next.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman7Activity.class);
            startActivity(intent);
        });

        // ▶ Czerwona słuchawka → KONIEC modułu
        hangup.setOnClickListener(v -> {
            finish();
        });
    }
}
