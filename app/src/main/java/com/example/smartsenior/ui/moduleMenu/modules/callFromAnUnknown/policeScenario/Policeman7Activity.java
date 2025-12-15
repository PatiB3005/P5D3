package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.CallFromAnUnknownMenuActivity;

public class Policeman7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_7);

        Button finish = findViewById(R.id.btnFinish);

        // ▶ Przycisk „Zakończ” – kończy moduł i wraca do poprzedniego ekranu
        finish.setOnClickListener(v -> {
            Intent intent = new Intent(this, CallFromAnUnknownMenuActivity.class);
            startActivity(intent);
        });
    }
}
