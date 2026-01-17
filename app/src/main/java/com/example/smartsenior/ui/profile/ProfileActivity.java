package com.example.smartsenior.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.ProfileManager;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private EditText inputName, inputSurname, inputAge;
    private Button btnEditSave, btnBack;
    private boolean isEditing = false;

    private LinearLayout medalsLayout;
    private TextView labelMedal;
    private ProfileManager profileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        inputName = findViewById(R.id.inputName);
        inputSurname = findViewById(R.id.inputSurname);
        inputAge = findViewById(R.id.inputAge);
        btnEditSave = findViewById(R.id.btnEditSave);
        btnBack = findViewById(R.id.backButton);

        labelMedal = findViewById(R.id.labelMedal);
        medalsLayout = findViewById(R.id.medalsLayout);

        profileManager = new ProfileManager(this);

        loadProfileData();
        setFieldsEnabled(false);
        setupButtons();
        updateMedals();
    }

    private void setupButtons() {
        btnEditSave.setOnClickListener(v -> {
            if (!isEditing) {
                isEditing = true;
                setFieldsEnabled(true);
                btnEditSave.setText("Zapisz");
            } else {
                isEditing = false;
                setFieldsEnabled(false);
                btnEditSave.setText("Edytuj");
                saveProfileData();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void setFieldsEnabled(boolean enabled) {
        inputName.setEnabled(enabled);
        inputSurname.setEnabled(enabled);
        inputAge.setEnabled(enabled);
    }

    private void saveProfileData() {
        String name = inputName.getText().toString();
        String surname = inputSurname.getText().toString();
        String age = inputAge.getText().toString();
        profileManager.saveProfile(name, surname, age);
    }

    private void loadProfileData() {
        inputName.setText(profileManager.getName());
        inputSurname.setText(profileManager.getSurname());
        inputAge.setText(profileManager.getAge());
    }

    private void updateMedals() {
        List<String> medals = profileManager.getMedalsList();

        if (medals.isEmpty()) {
            medalsLayout.setVisibility(View.GONE);
            labelMedal.setVisibility(View.GONE);
            return;
        }

        medalsLayout.setVisibility(View.VISIBLE);
        labelMedal.setVisibility(View.VISIBLE);
        medalsLayout.removeAllViews();

        float density = getResources().getDisplayMetrics().density;
        int size = (int) (64 * density);
        int margin = (int) (4 * density);

        for (String type : medals) {
            ImageView iv = new ImageView(this);

            int resId;
            switch (type) {
                case "BRONZE":
                    resId = R.drawable.ic_medal_bronze;
                    break;
                case "SILVER":
                    resId = R.drawable.ic_medal_silver;
                    break;
                case "GOLD":
                    resId = R.drawable.ic_medal_gold;
                    break;
                default:
                    resId = R.drawable.ic_sad_emoji;
            }

            iv.setImageResource(resId);
            iv.setBackgroundResource(R.drawable.edittext_bg);

            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(size, size);
            lp.setMargins(margin, margin, margin, margin);
            iv.setLayoutParams(lp);

            medalsLayout.addView(iv);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMedals();
    }
}