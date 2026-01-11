package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class Task3Page2Activity extends BaseTTSActivity {

    MaterialCardView btn1Left, btn1Right;
    MaterialCardView btn2Left, btn2Right;
    MaterialCardView btn3Left, btn3Right;
    MaterialCardView btn4Left, btn4Right;

    TextView comment1, comment2, comment3, comment4;
    MaterialButton btnFinish;

    boolean q1done = false, q2done = false, q3done = false, q4done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3_page2);

        btn1Left = findViewById(R.id.btn1Left);
        btn1Right = findViewById(R.id.btn1Right);
        comment1 = findViewById(R.id.comment1);

        btn2Left = findViewById(R.id.btn2Left);
        btn2Right = findViewById(R.id.btn2Right);
        comment2 = findViewById(R.id.comment2);

        btn3Left = findViewById(R.id.btn3Left);
        btn3Right = findViewById(R.id.btn3Right);
        comment3 = findViewById(R.id.comment3);

        btn4Left = findViewById(R.id.btn4Left);
        btn4Right = findViewById(R.id.btn4Right);
        comment4 = findViewById(R.id.comment4);

        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setVisibility(View.GONE);
        btnFinish.setEnabled(false);

        btn1Left.setOnClickListener(v -> {
            tts.stop();
            showCorrect(btn1Left, btn1Right, comment1,
                    "Komentarz: „W fałszywej nazwie użyto dużej litery I zamiast małego l.”");
            q1done = true;
            checkAllDone();
        });

        btn1Right.setOnClickListener(v -> {
            tts.stop();
            showWrong(btn1Right, btn1Left, comment1,
                    "Komentarz: „W fałszywej nazwie użyto dużej litery I zamiast małego l.”");
            q1done = true;
            checkAllDone();
        });

        btn2Left.setOnClickListener(v -> {
            tts.stop();
            showCorrect(btn2Left, btn2Right, comment2,
                    "Komentarz: „Oszust zamienił litery ‘o’ na cyfry 0.”");
            q2done = true;
            checkAllDone();
        });

        btn2Right.setOnClickListener(v -> {
            tts.stop();
            showWrong(btn2Right, btn2Left, comment2,
                    "Komentarz: „Oszust zamienił litery ‘o’ na cyfry 0.”");
            q2done = true;
            checkAllDone();
        });

        btn3Left.setOnClickListener(v -> {
            tts.stop();
            showCorrect(btn3Left, btn3Right, comment3,
                    "Komentarz: „Zamiana litery na cyfrę — klasyczny trik oszustów.”");
            q3done = true;
            checkAllDone();
        });

        btn3Right.setOnClickListener(v -> {
            tts.stop();
            showWrong(btn3Right, btn3Left, comment3,
                    "Komentarz: „Zamiana litery na cyfrę — klasyczny trik oszustów.”");
            q3done = true;
            checkAllDone();
        });

        btn4Left.setOnClickListener(v -> {
            tts.stop();
            showCorrect(btn4Left, btn4Right, comment4,
                    "Komentarz: „Dodatkowe słowo ‘pay’ sugeruje fałszywy moduł płatności.”");
            q4done = true;
            checkAllDone();
        });

        btn4Right.setOnClickListener(v -> {
            tts.stop();
            showWrong(btn4Right, btn4Left, comment4,
                    "Komentarz: „Dodatkowe słowo ‘pay’ sugeruje fałszywy moduł płatności.”");
            q4done = true;
            checkAllDone();
        });

        btnFinish.setOnClickListener(v -> {
            tts.stop();
            startActivity(new Intent(Task3Page2Activity.this, TasksActivity.class));
            finish();
        });
    }

    private void showCorrect(MaterialCardView correct, MaterialCardView wrong,
                             TextView commentBox, String commentText) {

        correct.setCardBackgroundColor(getColor(android.R.color.holo_green_light));
        correct.setEnabled(false);
        wrong.setEnabled(false);

        commentBox.setText(commentText);
        commentBox.setVisibility(View.VISIBLE);
    }

    private void showWrong(MaterialCardView wrong, MaterialCardView correct,
                           TextView commentBox, String commentText) {

        wrong.setCardBackgroundColor(getColor(android.R.color.holo_red_light));
        wrong.setEnabled(false);
        correct.setEnabled(false);

        commentBox.setText(commentText);
        commentBox.setVisibility(View.VISIBLE);
    }

    private void checkAllDone() {
        if (q1done && q2done && q3done && q4done) {
            btnFinish.setVisibility(View.VISIBLE);
            btnFinish.setEnabled(true);
            btnFinish.setAlpha(1f);
        }
    }

    @Override
    protected String getSpeakText() {
        return collectSpeakableTextFromLayout();
    }
}
