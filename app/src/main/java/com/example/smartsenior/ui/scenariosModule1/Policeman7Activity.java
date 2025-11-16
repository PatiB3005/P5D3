package com.example.smartsenior.ui.scenariosModule1;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_7);

        Button finish = findViewById(R.id.btnFinish);

        // ▶ Przycisk „Zakończ” – kończy moduł i wraca do poprzedniego ekranu
        finish.setOnClickListener(v -> {
            finish();
        });
    }
}
