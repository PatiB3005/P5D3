package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;

public class Website6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_6);

        Button btnFake = findViewById(R.id.btnFake);
        Button btnReal = findViewById(R.id.btnReal);

        btnFake.setOnClickListener(v -> {
            startActivity(new Intent(Website6Activity.this, Module3Activity.class));
        });

        btnReal.setOnClickListener(v -> {
            startActivity(new Intent(Website6Activity.this, Module3Activity.class));
        });
    }
}
