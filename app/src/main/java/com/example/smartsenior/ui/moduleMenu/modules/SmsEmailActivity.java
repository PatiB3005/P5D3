package com.example.smartsenior.ui.moduleMenu.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.BaseTTSActivity;

public class SmsEmailActivity extends BaseTTSActivity {

    private static final String TAG = "SmsEmailActivity";

    private static final int INTRO_SCREEN = 1;
    private static final int FIRST_QUESTION_SCREEN = 2;
    private static final int LAST_QUESTION_SCREEN = 9;

    private static final int SCREEN_GOLD = 10;
    private static final int SCREEN_SILVER = 11;
    private static final int SCREEN_BROWN = 12;
    private static final int SCREEN_RETURN = 13;

    private int currentScreen = INTRO_SCREEN;
    private int score = 0;

    // zabezpieczenie przed podwójnym czytaniem startowego ekranu
    private boolean firstScreenAlreadyShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
        firstScreenAlreadyShown = true;
    }

    private void showScreen(int screenNumber) {
        // zatrzymaj poprzednie czytanie przed podmianą layoutu
        tts.stop();

        switch (screenNumber) {
            case 1: setContentView(R.layout.activity_sms_1); break;
            case 2: setContentView(R.layout.activity_sms_2); break;
            case 3: setContentView(R.layout.activity_sms_3); break;
            case 4: setContentView(R.layout.activity_sms_4); break;
            case 5: setContentView(R.layout.activity_sms_5); break;
            case 6: setContentView(R.layout.activity_sms_6); break;
            case 7: setContentView(R.layout.activity_sms_7); break;
            case 8: setContentView(R.layout.activity_sms_8); break;
            case 9: setContentView(R.layout.activity_sms_9); break;
            case 10: setContentView(R.layout.activity_sms_gold); break;
            case 11: setContentView(R.layout.activity_sms_silver); break;
            case 12: setContentView(R.layout.activity_sms_brown); break;
            case 13: setContentView(R.layout.activity_sms_return); break;
        }

        setupButtons();
        setupAnswerButtons();

        // KLUCZOWE: po zmianie layoutu trzeba ręcznie uruchomić lektora
        if (firstScreenAlreadyShown) {
            getWindow().getDecorView().post(this::speakIfEnabled);
        }
    }

    private void setupAnswerButtons() {
        View yes = findViewById(R.id.yesButton);
        View no = findViewById(R.id.noButton);

        if (yes == null || no == null) return;

        yes.setOnClickListener(v -> {
            tts.stop();
            boolean correct = isCorrectAnswer(currentScreen, true);
            goNextQuestion(correct);
        });

        no.setOnClickListener(v -> {
            tts.stop();
            boolean correct = isCorrectAnswer(currentScreen, false);
            goNextQuestion(correct);
        });
    }

    private boolean isCorrectAnswer(int screenNumber, boolean answeredYes) {
        switch (screenNumber) {
            case 2:  return !answeredYes;
            case 3:  return !answeredYes;
            case 4:  return !answeredYes;
            case 5:  return answeredYes;
            case 6:  return !answeredYes;
            case 7:  return answeredYes;
            case 8:  return !answeredYes;
            case 9:  return answeredYes;
            default: return false;
        }
    }

    private void goNextQuestion(boolean correct) {
        if (currentScreen >= FIRST_QUESTION_SCREEN && currentScreen <= LAST_QUESTION_SCREEN) {
            if (correct) score++;
            Log.d(TAG, "Screen=" + currentScreen + " correct=" + correct + " score=" + score);
        }

        if (currentScreen == LAST_QUESTION_SCREEN) {
            goToResultScreen();
            return;
        }

        currentScreen++;
        showScreen(currentScreen);
    }

    private void goToResultScreen() {
        Log.d(TAG, "FINAL SCORE=" + score);

        if (score >= 8) currentScreen = SCREEN_GOLD;
        else if (score >= 5) currentScreen = SCREEN_SILVER;
        else if (score >= 4) currentScreen = SCREEN_BROWN;
        else currentScreen = SCREEN_RETURN;

        showScreen(currentScreen);
    }

    private void setupButtons() {

        View next = findViewById(R.id.btnNext);
        if (next != null) {
            next.setOnClickListener(v -> {
                tts.stop();

                if (currentScreen == INTRO_SCREEN) {
                    currentScreen = FIRST_QUESTION_SCREEN;
                    showScreen(currentScreen);
                    return;
                }

                if (currentScreen < LAST_QUESTION_SCREEN) {
                    currentScreen++;
                    showScreen(currentScreen);
                } else if (currentScreen == LAST_QUESTION_SCREEN) {
                    goToResultScreen();
                }
            });
        }

        View back = findViewById(R.id.btnBack);
        if (back != null) {
            back.setOnClickListener(v -> {
                tts.stop();

                if (currentScreen > INTRO_SCREEN) {
                    currentScreen--;
                    showScreen(currentScreen);
                } else {
                    finish();
                }
            });
        }

        View retryTestButton = findViewById(R.id.retryTestButton);
        if (retryTestButton != null) {
            retryTestButton.setOnClickListener(v -> {
                tts.stop();
                score = 0;
                currentScreen = INTRO_SCREEN;
                showScreen(currentScreen);
            });
        }

        View backToMenuButton = findViewById(R.id.backToMenuButton);
        if (backToMenuButton != null) {
            backToMenuButton.setOnClickListener(v -> {
                tts.stop();
                finish();
            });
        }
    }

    @Override
    protected String getSpeakText() {
        String base = collectSpeakableTextFromLayout();

        View yes = findViewById(R.id.yesButton);
        View no = findViewById(R.id.noButton);

        // Uwaga: jeśli to są Button/MaterialButton, to nie są TextView -> wtedy będzie pusty tekst.
        // Wtedy trzeba pobrać tekst inaczej (patrz komentarz poniżej).
        String yesText = (yes instanceof TextView) ? ((TextView) yes).getText().toString().trim() : "";
        String noText = (no instanceof TextView) ? ((TextView) no).getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (!base.isEmpty()) sb.append(base);

        if (!yesText.isEmpty() || !noText.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Opcje: ").append(yesText).append(", ").append(noText);
        }

        return sb.toString().trim();
    }
}
