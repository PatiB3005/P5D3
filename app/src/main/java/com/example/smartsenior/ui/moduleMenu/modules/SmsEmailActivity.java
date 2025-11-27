package com.example.smartsenior.ui.moduleMenu.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

import android.util.Log;


public class SmsEmailActivity extends AppCompatActivity {

    private int currentScreen = 1;  // ekrany 1–8
    private int score = 0;          // licznik punktów

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
    }

    private void showScreen(int screenNumber) {
        switch (screenNumber) {
            case 1:
                setContentView(R.layout.activity_sms_1);
                break;
            case 2:
                setContentView(R.layout.activity_sms_2);
                break;
            case 3:
                setContentView(R.layout.activity_sms_3);
                break;
            case 4:
                setContentView(R.layout.activity_sms_4);
                break;
            case 5:
                setContentView(R.layout.activity_sms_5);
                break;
            case 6:
                setContentView(R.layout.activity_sms_6);
                break;
            case 7:
                setContentView(R.layout.activity_sms_7);
                break;
            case 8:
                setContentView(R.layout.activity_sms_8);
                break;
            case 9:
                setContentView(R.layout.activity_sms_9);
                break;
            case 10:
                setContentView(R.layout.activity_sms_gold);
                break;
            case 11:
                setContentView(R.layout.activity_sms_silver);
                break;
            case 12:
                setContentView(R.layout.activity_sms_brown);
                break;
            case 13:
                setContentView(R.layout.activity_sms_return);
                break;
        }

        setupButtons();
        setupAnswerButtons();
    }

    /**
     * Obsługa przycisków TAK / NIE
     * Poprawna odpowiedź to NO → +1 punkt
     */
    private void setupAnswerButtons() {

        View yes = findViewById(R.id.yesButton);
        View no = findViewById(R.id.noButton);

        // JEŚLI OBECNY EKRAN NIE MA PRZYCISKÓW TAK/NIE — WYJDŹ
        if (yes == null || no == null) {
            return;
        }

        yes.setOnClickListener(v -> {
            yes.setBackgroundResource(R.drawable.answer_selected);
            no.setBackgroundResource(R.drawable.answer_default);
            goNextQuestion(false);
        });

        no.setOnClickListener(v -> {
            no.setBackgroundResource(R.drawable.answer_selected);
            yes.setBackgroundResource(R.drawable.answer_default);
            goNextQuestion(true);
        });
    }


    /**
     * Przejście do kolejnego pytania i zliczenie punktów
     */
    private void goNextQuestion(boolean correct) {

        if (correct) score++;

        // Jeśli jesteśmy na ostatnim pytaniu
        if (currentScreen == 9) {
            goToResultScreen();
            return;
        }

        // Jeśli są kolejne ekrany
        currentScreen++;
        showScreen(currentScreen);
    }

    /**
     * Wybór ekranu końcowego na podstawie liczby punktów
     */
    private void goToResultScreen() {


        if (score >= 7) {
            currentScreen = 10;   // GOLD
        } else if (score >= 5) {
            currentScreen = 11;   // SILVER
        } else if (score >= 3) {
            currentScreen = 12;   // BROWN
        } else {
            currentScreen = 13;   // RETURN (fail)
        }

        showScreen(currentScreen);
    }

    /**
     * Obsługa przycisków NEXT / BACK w każdym layoucie
     */
    private void setupButtons() {

        // NEXT
        View next = findViewById(R.id.btnNext);
        if (next != null) {
            next.setOnClickListener(v -> {

                if (currentScreen < 9) {
                    currentScreen++;
                    showScreen(currentScreen);
                } else {
                    goToResultScreen();
                }
            });
        }

        // BACK
        View back = findViewById(R.id.btnBack);
        if (back != null) {
            back.setOnClickListener(v -> {
                if (currentScreen > 1) {
                    currentScreen--;
                    showScreen(currentScreen);
                } else {
                    finish();
                }
            });
        }

        // Powrót do menu – jeśli taki przycisk istnieje
        View retryTestButton = findViewById(R.id.retryTestButton);
        if (retryTestButton != null) {
            retryTestButton.setOnClickListener(v -> {
                currentScreen = 1;
                showScreen(currentScreen);
            });
        }

        View backToMenuButton = findViewById(R.id.backToMenuButton);
        if (backToMenuButton != null) {
            backToMenuButton.setOnClickListener(v -> {
               finish();
            });
        }
    }



}
