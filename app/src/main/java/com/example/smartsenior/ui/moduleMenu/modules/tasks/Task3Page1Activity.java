package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class Task3Page1Activity extends AppCompatActivity {

    MaterialCardView btn1Left, btn1Right;
    MaterialCardView btn2Left, btn2Right;
    MaterialCardView btn3Left, btn3Right;
    MaterialCardView btn4Left, btn4Right;

    TextView comment1, comment2, comment3, comment4;
    MaterialButton btnNext;

    boolean q1done = false, q2done = false, q3done = false, q4done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3_page1);

        // POWIĄZANIA Z XML
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

        btnNext = findViewById(R.id.btnNext);


        // ===================== PARA 1 =====================
        btn1Left.setOnClickListener(v -> {
            showCorrect(btn1Left, btn1Right, comment1,
                    "Komentarz: „rn to nie m — fałszywa nazwa.”");
            q1done = true;
            checkAllDone();
        });

        btn1Right.setOnClickListener(v -> {
            showWrong(btn1Right, btn1Left, comment1,
                    "Komentarz: „rn to nie m — fałszywa nazwa.”");
            q1done = true;
            checkAllDone();
        });


        // ===================== PARA 2 =====================
        btn2Left.setOnClickListener(v -> {
            showCorrect(btn2Left, btn2Right, comment2,
                    "Komentarz: „Dodatkowe słowa w adresie to częsty trik oszustów.”");
            q2done = true;
            checkAllDone();
        });

        btn2Right.setOnClickListener(v -> {
            showWrong(btn2Right, btn2Left, comment2,
                    "Komentarz: „Dodatkowe słowa w adresie to częsty trik oszustów.”");
            q2done = true;
            checkAllDone();
        });


        // ===================== PARA 3 =====================
        btn3Left.setOnClickListener(v -> {
            showCorrect(btn3Left, btn3Right, comment3,
                    "Komentarz: „Prawdziwe firmy mają końcówkę .pl lub .com.”");
            q3done = true;
            checkAllDone();
        });

        btn3Right.setOnClickListener(v -> {
            showWrong(btn3Right, btn3Left, comment3,
                    "Komentarz: „Prawdziwe firmy mają końcówkę .pl lub .com.”");
            q3done = true;
            checkAllDone();
        });


        // ===================== PARA 4 =====================
        btn4Left.setOnClickListener(v -> {
            showCorrect(btn4Left, btn4Right, comment4,
                    "Komentarz: „Fałszywe strony Poczty Polskiej to częste oszustwo.”");
            q4done = true;
            checkAllDone();
        });

        btn4Right.setOnClickListener(v -> {
            showWrong(btn4Right, btn4Left, comment4,
                    "Komentarz: „Fałszywe strony Poczty Polskiej to częste oszustwo.”");
            q4done = true;
            checkAllDone();
        });


        // ===================== DALEJ =====================
        btnNext.setOnClickListener(v ->
                startActivity(new Intent(Task3Page1Activity.this, Task3Page2Activity.class))
        );
    }


    // ===================== FUNKCJE =====================

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

            // Przycisk pojawia się dopiero po 4 odpowiedziach
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setEnabled(true);
            btnNext.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));
        }
    }
}
