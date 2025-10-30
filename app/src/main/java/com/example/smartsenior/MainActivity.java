package com.example.smartsenior;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.example.smartsenior.data.ProfileManager;
import com.example.smartsenior.ui.tutorial.TutorialActivity1;

public class MainActivity extends AppCompatActivity {

    private ProfileManager profileManager;
    private EditText editName;
    private TextView tvProgress;
    private Button btnSave, btnLoad, btnTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileManager = new ProfileManager(this);

        editName = findViewById(R.id.editName);
        tvProgress = findViewById(R.id.tvProgress);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);

        // 🔹 Tworzymy nowy przycisk do testu samouczka
        btnTutorial = new Button(this);
        btnTutorial.setText("Otwórz samouczek");
        btnTutorial.setAllCaps(false);

        // 🔹 Dodajemy go dynamicznie do layoutu
        addContentView(btnTutorial, new android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

        // 🔹 Logika dla pozostałych przycisków
        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString();
            profileManager.saveProfile(name, 50); // przykładowy postęp
        });

        btnLoad.setOnClickListener(v -> {
            String name = profileManager.getName();
            int progress = profileManager.getProgress();
            tvProgress.setText("Użytkownik: " + name + "\nPostęp: " + progress + "%");
        });

        // 🔹 Po kliknięciu otwiera Twój ekran TutorialActivity1
        btnTutorial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TutorialActivity1.class);
            startActivity(intent);
        });
    }
}
