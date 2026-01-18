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
import com.example.smartsenior.data.ProfileManager.MedalInfo;
import com.example.smartsenior.data.InfoPopup;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private EditText inputName, inputSurname, inputAge;
    private Button btnEditSave, btnBack;
    private boolean isEditing = false;

    private LinearLayout medalsLayout;
    private TextView labelMedal;
    private ProfileManager profileManager;
    private InfoPopup infoPopup;

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

        try {
            infoPopup = new InfoPopup(this);
        } catch (Exception e) {
            android.util.Log.w("ProfileActivity", "InfoPopup not available - popupOverlay missing in layout");
            infoPopup = null;
        }

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
        try {
            List<MedalInfo> medals = profileManager.getMedalsInfo();

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

            for (MedalInfo medal : medals) {
                ImageView iv = new ImageView(this);
                iv.setImageResource(getMedalDrawable(medal.type));
                iv.setBackgroundResource(R.drawable.edittext_bg);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                lp.setMargins(margin, margin, margin, margin);
                iv.setLayoutParams(lp);

                iv.setOnClickListener(v -> showMedalPopup(medal));

                medalsLayout.addView(iv);
            }
        } catch (Exception e) {
            android.util.Log.e("ProfileActivity", "Error loading medals", e);
            updateMedalsOldWay();
        }
    }

    private void updateMedalsOldWay() {
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
            iv.setImageResource(getMedalDrawable(type));
            iv.setBackgroundResource(R.drawable.edittext_bg);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
            lp.setMargins(margin, margin, margin, margin);
            iv.setLayoutParams(lp);

            medalsLayout.addView(iv);
        }
    }

    private int getMedalDrawable(String type) {
        switch (type) {
            case "BRONZE": return R.drawable.ic_medal_bronze;
            case "SILVER": return R.drawable.ic_medal_silver;
            case "GOLD": return R.drawable.ic_medal_gold;
            default: return R.drawable.ic_sad_emoji;
        }
    }

    private void showMedalPopup(MedalInfo medal) {
        if (infoPopup != null) {
            infoPopup.setOnDismissListener(() -> {});
            infoPopup.show(medal.getTitle(), medal.getDescription());
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(medal.getTitle())
                    .setMessage(medal.getDescription())
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMedals();
    }
}