package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        LinearLayout task1 = findViewById(R.id.btnTask1);
        LinearLayout task2 = findViewById(R.id.btnTask2);
        LinearLayout task3 = findViewById(R.id.btnTask3);
        LinearLayout task4 = findViewById(R.id.btnTask4);

        task1.setOnClickListener(v ->
                startActivity(new Intent(this, Task1Activity.class)));

        task2.setOnClickListener(v ->
                startActivity(new Intent(this, Task2Activity.class)));

        task3.setOnClickListener(v ->
                startActivity(new Intent(this, Task3Activity.class)));

        task4.setOnClickListener(v ->
                startActivity(new Intent(this, Task4Activity.class)));
    }
}
