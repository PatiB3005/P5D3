package com.example.smartsenior.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.R;

public class TutorialActivity5 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_5);

        Button nextButton = findViewById(R.id.button_dalej);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(TutorialActivity5.this, TutorialActivity6.class);
            startActivity(intent);
        });
    }
}
