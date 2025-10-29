package com.example.smartsenior.ui.moduleMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module1Activity;

public class ModuleMenuActivity extends AppCompatActivity {

    private LinearLayout btnModule1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_menu);

        btnModule1 = findViewById(R.id.btnModule1);

        btnModule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleMenuActivity.this, Module1Activity.class);
                startActivity(intent);
            }
        });
    }


}
