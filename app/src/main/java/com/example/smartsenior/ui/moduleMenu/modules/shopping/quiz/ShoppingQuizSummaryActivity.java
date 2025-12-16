package com.example.smartsenior.ui.moduleMenu.modules.shopping.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.moduleMenu.modules.Module3Activity;
import com.google.android.material.button.MaterialButton;

public class ShoppingQuizSummaryActivity extends AppCompatActivity {

    private TextView summaryScore, summaryMessage;
    private MaterialButton btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_quiz_summary);

        summaryScore = findViewById(R.id.summaryScore);
        summaryMessage = findViewById(R.id.summaryMessage);
        btnFinish = findViewById(R.id.btnFinish);

        int score = getIntent().getIntExtra("QUIZ_RESULT", 0);

        summaryScore.setText("Twój wynik: " + score + "/8");

        if (score == 8) {
            summaryMessage.setText("Świetnie! Doskonale rozpoznajesz fałszywe sklepy.");
        } else if (score >= 5) {
            summaryMessage.setText("Bardzo dobrze! Masz dobrą intuicję, ale warto zachować czujność.");
        } else {
            summaryMessage.setText("Uważaj! Warto przejrzeć lekcję jeszcze raz, aby robić bezpieczne zakupy.");
        }

        btnFinish.setOnClickListener(v -> {
            // ZALICZENIE CZĘŚCI: quiz zakupów online ukończony
            ProgressStore.markDone(this, ProgressKeys.M3_QUIZ_DONE);

            Intent intent = new Intent(ShoppingQuizSummaryActivity.this, Module3Activity.class);
            startActivity(intent);
            finish();
        });
    }
}
