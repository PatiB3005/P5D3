package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.MainActivity;
import com.example.smartsenior.R;

public class TutorialActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_6);

        Button finishButton = findViewById(R.id.button_zakoncz);
        finishButton.setOnClickListener(v -> {
            getSharedPreferences("tutorial_progress", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
            // Przejście do menu głównego (MainActivity)
            Intent intent = new Intent(TutorialActivity6.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // zamyka samouczek
        });
    }


}
