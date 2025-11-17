package com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.policeScenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class Policeman1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeman_1);

        ImageView call = findViewById(R.id.imgCall);

        Button end = findViewById(R.id.btnEnd);

        call.setOnClickListener(v -> {
            Intent intent = new Intent(this, Policeman2Activity.class);
            startActivity(intent);
        });

        end.setOnClickListener(v -> {
            finish();
        });
    }
}
