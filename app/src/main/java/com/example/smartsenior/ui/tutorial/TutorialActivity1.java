package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.R;

public class TutorialActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sprawdź, czy użytkownik już przeszedł część tutoriala
        SharedPreferences prefs = getSharedPreferences("tutorial_progress", MODE_PRIVATE);
        int lastCompleted = prefs.getInt("last_completed", 0);

        if (lastCompleted == 3) {
            // Użytkownik zakończył ekran 3, więc kontynuujemy od 4
            Intent intent = new Intent(this, TutorialActivity4.class);
            startActivity(intent);
            finish();
            return;
        }

        // Jeśli nie ma postępu, pokaż pierwszy ekran
        setContentView(R.layout.activity_tutorial_1);

        Button startButton = findViewById(R.id.button_zaczynamy);
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(TutorialActivity1.this, TutorialActivity2.class);
            startActivity(intent);
        });
    }
}
