package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Website2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_2);

        // Pobranie przycisków z layoutu
        Button btnFake = findViewById(R.id.btnFake);
        Button btnReal = findViewById(R.id.btnReal);

        btnFake.setOnClickListener(v -> {
            startActivity(new Intent(Website2Activity.this, Website3Activity.class));
        });

        btnReal.setOnClickListener(v -> {
            startActivity(new Intent(Website2Activity.this, Website3Activity.class));
        });
    }
}
