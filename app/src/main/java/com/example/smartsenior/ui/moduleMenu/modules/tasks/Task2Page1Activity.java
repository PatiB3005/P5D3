package com.example.smartsenior.ui.moduleMenu.modules.tasks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class Task2Page1Activity extends AppCompatActivity {

    // Pytanie 1
    MaterialButton btnQ1True, btnQ1False;
    View commentQ1;

    // Pytanie 2
    MaterialButton btnQ2True, btnQ2False;
    View commentQ2;

    // Pytanie 3
    MaterialButton btnQ3True, btnQ3False;
    View commentQ3;

    // Dalej
    MaterialButton btnNext;

    // Kontrola – czy odpowiedziano na pytania
    boolean q1Answered = false;
    boolean q2Answered = false;
    boolean q3Answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2_page1);

        // ================== FIND VIEW ==================
        btnQ1True = findViewById(R.id.btnQ1True);
        btnQ1False = findViewById(R.id.btnQ1False);
        commentQ1 = findViewById(R.id.commentQ1);

        btnQ2True = findViewById(R.id.btnQ2True);
        btnQ2False = findViewById(R.id.btnQ2False);
        commentQ2 = findViewById(R.id.commentQ2);

        btnQ3True = findViewById(R.id.btnQ3True);
        btnQ3False = findViewById(R.id.btnQ3False);
        commentQ3 = findViewById(R.id.commentQ3);

        btnNext = findViewById(R.id.btnNext);


        // ================== PRZYCISKI PYTANIA 1 ==================
        btnQ1True.setOnClickListener(v -> handleAnswer(
                true,   // user clicked TRUE
                false,  // correct answer is FALSE
                btnQ1True, btnQ1False,
                commentQ1,
                "Źle! Bank nigdy nie prosi o kody SMS ani hasła. To próba oszustwa."
        ));

        btnQ1False.setOnClickListener(v -> handleAnswer(
                false,
                false,
                btnQ1True, btnQ1False,
                commentQ1,
                "Dobrze! Bank nigdy nie prosi o kody SMS ani hasła. To próba oszustwa."
        ));


        // ================== PRZYCISKI PYTANIA 2 ==================
        btnQ2True.setOnClickListener(v -> handleAnswer(
                true,
                true,
                btnQ2True, btnQ2False,
                commentQ2,
                "Dobrze! Oficjalne instytucje nie wysyłają wiadomości z błędami — to typowy znak oszustwa."
        ));

        btnQ2False.setOnClickListener(v -> handleAnswer(
                false,
                true,
                btnQ2True, btnQ2False,
                commentQ2,
                "Źle! Oficjalne instytucje nie wysyłają wiadomości z błędami — to typowy znak oszustwa."
        ));


        // ================== PRZYCISKI PYTANIA 3 ==================
        btnQ3True.setOnClickListener(v -> handleAnswer(
                true,
                false,
                btnQ3True, btnQ3False,
                commentQ3,
                "Źle! Kliknięcie w link może prowadzić na fałszywą stronę, która ukradnie Twoje dane."
        ));

        btnQ3False.setOnClickListener(v -> handleAnswer(
                false,
                false,
                btnQ3True, btnQ3False,
                commentQ3,
                "Dobrze! Kliknięcie w link może prowadzić na fałszywą stronę, która ukradnie Twoje dane."
        ));


        // ================== DALEJ ==================
        btnNext.setOnClickListener(v ->
                startActivity(new Intent(Task2Page1Activity.this, Task2Page2Activity.class))
        );
    }



    // =====================================================
    //                FUNKCJA OBSŁUGI ODPOWIEDZI
    // =====================================================
    private void handleAnswer(
            boolean userAnswer,
            boolean correctAnswer,
            MaterialButton btnTrue,
            MaterialButton btnFalse,
            View commentBox,
            String commentText
    ) {
        // Zablokuj oba przyciski, aby nie zmieniać odpowiedzi
        btnTrue.setClickable(false);
        btnFalse.setClickable(false);

        // Ustaw komentarz
        if (commentBox instanceof android.widget.TextView) {
            ((android.widget.TextView) commentBox).setText(commentText);
        }
        commentBox.setVisibility(View.VISIBLE);

        // Kolorowanie
        if (userAnswer == correctAnswer) {
            // poprawna odpowiedź = zielony
            if (userAnswer)
                btnTrue.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#86EFAC")));
            else
                btnFalse.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#86EFAC")));
        } else {
            // błędna odpowiedź = czerwony
            if (userAnswer)
                btnTrue.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FCA5A5")));
            else
                btnFalse.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#FCA5A5")));
        }

        // Ustaw, które pytanie zostało rozwiązane
        if (commentBox == commentQ1) q1Answered = true;
        if (commentBox == commentQ2) q2Answered = true;
        if (commentBox == commentQ3) q3Answered = true;

        // Sprawdź, czy wszystkie trzy są gotowe
        checkAllAnswered();
    }


    // =====================================================
    //             ODBLOKOWANIE PRZYCISKU DALEJ
    // =====================================================
    private void checkAllAnswered() {
        if (q1Answered && q2Answered && q3Answered) {
            btnNext.setVisibility(View.VISIBLE);
        }
    }
}
