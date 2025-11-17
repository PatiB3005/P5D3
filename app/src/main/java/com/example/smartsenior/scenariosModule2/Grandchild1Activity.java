package com.example.smartsenior.scenariosModule2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Grandchild1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandchild_1);

        ImageView call = findViewById(R.id.imgCall);
        Button end = findViewById(R.id.btnEnd);

        // Zielona słuchawka – start scenariusza (scena 2)
        call.setOnClickListener(v -> {
            Intent intent = new Intent(Grandchild1Activity.this,
                    Grandchild2Activity.class);
            startActivity(intent);
        });

        // "Koniec" – wyjście z modułu
        end.setOnClickListener(v -> finish());
    }
}
