package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;

public class Website4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_4);

        // Pobranie przycisków z layoutu
        Button btnFake = findViewById(R.id.btnFake);
        Button btnReal = findViewById(R.id.btnReal);

        btnFake.setOnClickListener(v -> {
            startActivity(new Intent(Website4Activity.this, Website5Activity.class));
        });

        btnReal.setOnClickListener(v -> {
            startActivity(new Intent(Website4Activity.this, Website5Activity.class));
        });
    }
}
