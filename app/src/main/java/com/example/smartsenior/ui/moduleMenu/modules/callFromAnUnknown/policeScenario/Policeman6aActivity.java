package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman6aActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_6a);

        Button next = findViewById(R.id.btnNext);

        // ▶ „Dalej” → ekran POUCZENIE (Policeman7Activity)
        next.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman7Activity.class);
            startActivity(intent);
        });

    }
}
