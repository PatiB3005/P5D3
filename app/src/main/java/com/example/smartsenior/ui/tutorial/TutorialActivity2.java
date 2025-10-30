package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.R;

public class TutorialActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_2);

        // Przycisk "Dalej ➜"
        Button nextButton = findViewById(R.id.button_dalej);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(TutorialActivity2.this, TutorialActivity3.class);
            startActivity(intent);
        });
    }
}
