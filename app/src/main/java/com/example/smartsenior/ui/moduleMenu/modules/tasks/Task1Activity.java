package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.tasks.TasksActivity;

public class Task1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnStart = findViewById(R.id.btnStart);

        // Powrót do listy zadań
        btnBack.setOnClickListener(v ->
                startActivity(new Intent(this, TasksActivity.class))
        );

        // Przejście do pierwszej strony zadania
        btnStart.setOnClickListener(v ->
                startActivity(new Intent(this, Task1Page1Activity.class)) // <- TU FORMALNA ZMIANA
        );
    }
}
