package com.example.smartsenior.ui.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.ProfileManager;

public class ProfileActivity extends AppCompatActivity {

    private ProfileManager profileManager;
    private EditText editName;
    private TextView tvProgress;
    private Button btnSave, btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profileManager = new ProfileManager(this);

        editName = findViewById(R.id.editName);
        tvProgress = findViewById(R.id.tvProgress);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);

        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString();
            profileManager.saveProfile(name, 50); // przykładowy postęp
        });

        btnLoad.setOnClickListener(v -> {
            String name = profileManager.getName();
            int progress = profileManager.getProgress();
            tvProgress.setText("Użytkownik: " + name + "\nPostęp: " + progress + "%");
        });
    }
}
