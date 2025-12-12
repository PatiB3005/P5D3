package com.example.smartsenior.ui.virtualAssistant;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class virtualAssistantActivity extends AppCompatActivity {

    private EditText questionInput;
    private Button btnAsk;
    private TextView answerLabel;
    private TextView answerArea;

    private AssistantEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_assistant);

        questionInput = findViewById(R.id.questionInput);
        btnAsk = findViewById(R.id.btnAsk);
        answerLabel = findViewById(R.id.answerLabel);
        answerArea = findViewById(R.id.answerArea);

        // Na start ukryj odpowiedź (opcjonalnie)
        answerLabel.setVisibility(View.GONE);
        answerArea.setVisibility(View.GONE);
        answerArea.setText("");

        try {
            // JSON wrzuć do: app/src/main/assets/qa_pl.json
            engine = new AssistantEngine(this, "qa_pl.json");
        } catch (Exception e) {
            e.printStackTrace();
            answerLabel.setVisibility(View.VISIBLE);
            answerArea.setVisibility(View.VISIBLE);
            answerLabel.setText("Błąd");
            answerArea.setText("Nie udało się wczytać bazy wiedzy (qa_pl.json).");
        }

        btnAsk.setOnClickListener(v -> {
            if (engine == null) return;

            String userQ = questionInput.getText() != null ? questionInput.getText().toString() : "";
            String response = engine.reply(userQ);

            answerLabel.setVisibility(View.VISIBLE);
            answerArea.setVisibility(View.VISIBLE);
            answerArea.setText(response);
        });
    }
}
