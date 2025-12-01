package com.example.smartsenior.ui.moduleMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module1Activity;
import com.example.smartsenior.ui.moduleMenu.modules.callFromAnUnknown.CallFromAnUnknownMenuActivity;
import com.example.smartsenior.ui.moduleMenu.modules.fakenews.FakeNewsModuleActivity;

public class ModuleMenuActivity extends AppCompatActivity {

    private LinearLayout btnSafeMessages;
    private LinearLayout btnCallFromAnUnknown;
    private LinearLayout btnFakeNewsModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_menu);

        btnSafeMessages = findViewById(R.id.btnSafeMessages);
        btnCallFromAnUnknown = findViewById(R.id.btnCallFromAnUnknown);
        btnFakeNewsModule = findViewById(R.id.btnFakeNewsModule);

        btnSafeMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModuleMenuActivity.this, Module1Activity.class));
            }
        });

        btnCallFromAnUnknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModuleMenuActivity.this, CallFromAnUnknownMenuActivity.class));
            }
        });

        btnFakeNewsModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModuleMenuActivity.this, FakeNewsModuleActivity.class));
            }
        });
    }
}
