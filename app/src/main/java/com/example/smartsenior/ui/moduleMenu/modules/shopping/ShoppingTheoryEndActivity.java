package com.example.smartsenior.ui.moduleMenu.modules.shopping;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz.ShoppingQuizIntroActivity;
import com.example.smartsenior.ui.moduleMenu.modules.shopping.WebsiteActivity;

import com.google.android.material.button.MaterialButton;

public class ShoppingTheoryEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_theory_end);

        MaterialButton btnQuiz = findViewById(R.id.btnQuiz);
        MaterialButton btnPages = findViewById(R.id.btnPages);
        MaterialButton btnExit = findViewById(R.id.btnExit);

        btnQuiz.setOnClickListener(v -> {
            startActivity(new Intent(this, ShoppingQuizIntroActivity.class));
        });

        btnPages.setOnClickListener(v -> {
            startActivity(new Intent(this, WebsiteActivity.class));
        });

        btnExit.setOnClickListener(v -> {
            startActivity(new Intent(this, Module3Activity.class));
        });
    }
}
