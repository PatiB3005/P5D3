package com.example.smartsenior.ui.moduleMenu.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;

public class SmsEmailActivity extends AppCompatActivity {

    private static final String TAG = "SmsEmailActivity";

    // 1 = wstęp, 2-9 = pytania
    private static final int INTRO_SCREEN = 1;
    private static final int FIRST_QUESTION_SCREEN = 2;
    private static final int LAST_QUESTION_SCREEN = 9;

    // wyniki
    private static final int SCREEN_GOLD = 10;
    private static final int SCREEN_SILVER = 11;
    private static final int SCREEN_BROWN = 12;
    private static final int SCREEN_RETURN = 13;

    private int currentScreen = INTRO_SCREEN;
    private int score = 0;

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
     * Klik TAK/NIE = od razu licz punkt i przejdź dalej (wersja A)
     */
    private void setupAnswerButtons() {
        View yes = findViewById(R.id.yesButton);
        View no = findViewById(R.id.noButton);

        // jeśli ekran nie ma przycisków TAK/NIE (np. wstęp lub wynik) -> nic nie rób
        if (yes == null || no == null) return;

        yes.setOnClickListener(v -> {
            // (opcjonalnie) wizualne zaznaczenie
            // yes.setBackgroundResource(R.drawable.answer_selected);
            // no.setBackgroundResource(R.drawable.answer_default);

            boolean correct = isCorrectAnswer(currentScreen, true);
            goNextQuestion(correct);
        });

        no.setOnClickListener(v -> {
            // (opcjonalnie) wizualne zaznaczenie
            // no.setBackgroundResource(R.drawable.answer_selected);
            // yes.setBackgroundResource(R.drawable.answer_default);

            boolean correct = isCorrectAnswer(currentScreen, false);
            goNextQuestion(correct);
        });
    }

    /**
     * Tu ustawiasz, która odpowiedź jest poprawna na danym ekranie pytania.
     * screen 2-9 = pytania (8 szt.)
     */
    private boolean isCorrectAnswer(int screenNumber, boolean answeredYes) {
        // answeredYes == true -> kliknięto TAK
        // answeredYes == false -> kliknięto NIE

        switch (screenNumber) {
            case 2:  return !answeredYes; // Q1: NIE
            case 3:  return !answeredYes; // Q2: NIE
            case 4:  return !answeredYes; // Q3: NIE
            case 5:  return answeredYes;  // Q4: TAK
            case 6:  return !answeredYes; // Q5: NIE
            case 7:  return answeredYes;  // Q6: TAK
            case 8:  return !answeredYes; // Q7: NIE
            case 9:  return answeredYes;  // Q8: TAK
            default: return false;
        }
    }

    /**
     * Przejście do kolejnego pytania i zliczenie punktów
     */
    private void goNextQuestion(boolean correct) {
        if (currentScreen >= FIRST_QUESTION_SCREEN && currentScreen <= LAST_QUESTION_SCREEN) {
            if (correct) score++;
            Log.d(TAG, "Screen=" + currentScreen + " correct=" + correct + " score=" + score);
        }

        // jeśli to było ostatnie pytanie -> wyniki
        if (currentScreen == LAST_QUESTION_SCREEN) {
            goToResultScreen();
            return;
        }

        // kolejne ekrany
        currentScreen++;
        showScreen(currentScreen);
    }

    /**
     * Wybór ekranu końcowego na podstawie liczby punktów
     * GOLD = 8
     * SILVER = 5-7
     * BROWN = 4
     * RETURN = 0-3
     */
    private void goToResultScreen() {
        Log.d(TAG, "FINAL SCORE=" + score);

        if (score >= 8) {
            currentScreen = SCREEN_GOLD;
        } else if (score >= 5) {
            currentScreen = SCREEN_SILVER;
        } else if (score >= 4) {
            currentScreen = SCREEN_BROWN;
        } else {
            currentScreen = SCREEN_RETURN;
        }

        showScreen(currentScreen);
    }

    /**
     * Obsługa przycisków NEXT / BACK (jeśli istnieją w layoucie)
     */
    private void setupButtons() {

        // NEXT (np. na ekranie wstępu)
        View next = findViewById(R.id.btnNext);
        if (next != null) {
            next.setOnClickListener(v -> {
                // jeśli jesteśmy na wstępie -> przejdź do pierwszego pytania
                if (currentScreen == INTRO_SCREEN) {
                    currentScreen = FIRST_QUESTION_SCREEN;
                    showScreen(currentScreen);
                    return;
                }

                // jeśli ktoś jednak ma btnNext na pytaniach, to przejdzie dalej bez punktu
                if (currentScreen < LAST_QUESTION_SCREEN) {
                    currentScreen++;
                    showScreen(currentScreen);
                } else if (currentScreen == LAST_QUESTION_SCREEN) {
                    goToResultScreen();
                }
            });
        }

        // BACK
        View back = findViewById(R.id.btnBack);
        if (back != null) {
            back.setOnClickListener(v -> {
                if (currentScreen > INTRO_SCREEN) {
                    currentScreen--;
                    showScreen(currentScreen);
                } else {
                    finish();
                }
            });
        }

        // Powtórz test
        View retryTestButton = findViewById(R.id.retryTestButton);
        if (retryTestButton != null) {
            retryTestButton.setOnClickListener(v -> {
                score = 0; // !!! WAŻNE: reset punktów
                currentScreen = INTRO_SCREEN;
                showScreen(currentScreen);
            });
        }

        // Menu główne
        View backToMenuButton = findViewById(R.id.backToMenuButton);
        if (backToMenuButton != null) {
            backToMenuButton.setOnClickListener(v -> finish());
        }
    }
}
