package com.example.smartsenior;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.smartsenior.ui.help.HelpRequestManager;
import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;
import com.example.smartsenior.ui.moduleMenu.ModuleMenuActivity;
import com.example.smartsenior.ui.notifications.NotificationsActivity;
import com.example.smartsenior.ui.profile.ProfileActivity;
import com.example.smartsenior.ui.settings.SettingsActivity;
import com.example.smartsenior.ui.trustedContacts.TrustedContactsActivity;
import com.example.smartsenior.ui.trustedContacts.TrustedContactsStorage;
import com.example.smartsenior.ui.tutorial.TutorialActivity1;
import com.example.smartsenior.ui.virtualAssistant.virtualAssistantActivity;
import com.example.smartsenior.utils.TripleTapHelper;

public class MainActivity extends AppCompatActivity {

    private Button btnModuleMenu, btnProfile, btnMiniGames, btnWirtualAssistant,
            btnTutorial, btnNotifications, btnSettings, btnExit, btnTrustedContacts;

    private final TripleTapHelper tripleTap = new TripleTapHelper(1300);

    private HelpRequestManager helpManager;

    private final ActivityResultLauncher<String> smsPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (helpManager != null) {
                    helpManager.onSmsPermissionResult(granted);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnProfile          = findViewById(R.id.btnProfile);
        btnTutorial         = findViewById(R.id.btnTutorial);
        btnModuleMenu       = findViewById(R.id.btnModulesMenu);
        btnMiniGames        = findViewById(R.id.btnMiniGames);
        btnWirtualAssistant = findViewById(R.id.btnWirtualAssistant);
        btnTrustedContacts  = findViewById(R.id.btnTrustedContacts);
        btnNotifications    = findViewById(R.id.btnNotifications);
        btnSettings         = findViewById(R.id.btnSettings);
        btnExit             = findViewById(R.id.btnExit);

        helpManager = new HelpRequestManager(this, smsPermissionLauncher);

        findViewById(R.id.btnHelp).setOnClickListener(v -> {
            helpManager.startHelpFlow();
        });

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ProfileActivity.class)));

        btnTutorial.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TutorialActivity1.class)));

        btnModuleMenu.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ModuleMenuActivity.class)));

        btnMiniGames.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MiniGamesMenuActivity.class)));

        btnWirtualAssistant.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, virtualAssistantActivity.class)));

        btnTrustedContacts.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TrustedContactsActivity.class)));

        btnNotifications.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, NotificationsActivity.class)));

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        btnExit.setOnClickListener(v -> finishAffinity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyFontSize();
    }


    private void applyFontSize() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isLarge = prefs.getBoolean("large_font", false);

        float sizeNormal = 18f;
        float sizeBig = 24f;
        float sizeToUse = isLarge ? sizeBig : sizeNormal;

        setButtonTextSize(btnProfile, sizeToUse);
        setButtonTextSize(btnTutorial, sizeToUse);
        setButtonTextSize(btnModuleMenu, sizeToUse);
        setButtonTextSize(btnMiniGames, sizeToUse);
        setButtonTextSize(btnWirtualAssistant, sizeToUse);
        setButtonTextSize(btnTrustedContacts, sizeToUse);
        setButtonTextSize(btnNotifications, sizeToUse);
        setButtonTextSize(btnSettings, sizeToUse);
        setButtonTextSize(btnExit, sizeToUse);
    }

    private void setButtonTextSize(Button button, float sizeSp) {
        if (button != null) {
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (tripleTap.onTouch(this, ev)) {
            // nie "połykamy" eventu, żeby normalne klikanie dalej działało
        }
        return super.dispatchTouchEvent(ev);
    }
}
