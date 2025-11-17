package com.example.smartsenior.scenariosModule2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;

public class Grandchild7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_7);

        Button finishButton = findViewById(R.id.btnFinish);

        // "Zakończ" -> wraca do menu modułów
        finishButton.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild7Activity.this,
                    ModuleMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
