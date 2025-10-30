package com.example.smartsenior.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;  // ✅ brakujący import
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.smartsenior.R;

public class ProfileActivity extends AppCompatActivity {

    private EditText inputName, inputSurname, inputAge;
    private Button btnEditSave, btnBack;

    private boolean isEditing = false; // flaga – czy jesteśmy w trybie edycji

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        inputName = findViewById(R.id.inputName);
        inputSurname = findViewById(R.id.inputSurname);
        inputAge = findViewById(R.id.inputAge);
        btnEditSave = findViewById(R.id.btnEditSave);
        btnBack = findViewById(R.id.backButton);

        // Wczytaj dane zapisane wcześniej
        loadProfileData();

        // Na start blokujemy edycję pól
        setFieldsEnabled(false);

        // Obsługa przycisku Edytuj/Zapisz
        btnEditSave.setOnClickListener(v -> {
            if (!isEditing) {
                // Włącz tryb edycji
                isEditing = true;
                setFieldsEnabled(true);
                btnEditSave.setText("Zapisz");
            } else {
                // Wyłącz tryb edycji
                isEditing = false;
                setFieldsEnabled(false);
                btnEditSave.setText("Edytuj");

                saveProfileData();
            }
        });

        // Obsługa przycisku „Wróć do menu”
        btnBack.setOnClickListener(v -> finish());
    }

    private void setFieldsEnabled(boolean enabled) {
        inputName.setEnabled(enabled);
        inputSurname.setEnabled(enabled);
        inputAge.setEnabled(enabled);
    }

    private void saveProfileData() {
        SharedPreferences prefs = getSharedPreferences("profile", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", inputName.getText().toString());
        editor.putString("surname", inputSurname.getText().toString());
        editor.putString("age", inputAge.getText().toString());
        editor.apply(); // zapis
    }

    private void loadProfileData() {
        SharedPreferences prefs = getSharedPreferences("profile", MODE_PRIVATE);
        inputName.setText(prefs.getString("name", ""));
        inputSurname.setText(prefs.getString("surname", ""));
        inputAge.setText(prefs.getString("age", ""));
    }
}