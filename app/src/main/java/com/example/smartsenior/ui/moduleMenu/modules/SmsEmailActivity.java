package com.example.smartsenior.ui.moduleMenu.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.ProfileManager;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class SmsEmailActivity extends BaseTTSActivity {

    private static final String TAG = "SmsEmailActivity";

    private static final int INTRO_SCREEN = 1;
    private static final int FIRST_QUESTION_SCREEN = 2;
    private static final int LAST_QUESTION_SCREEN = 9;
    private static final int RESULT_SCREEN = 100; // wspólny ekran wyniku

    private int currentScreen = INTRO_SCREEN;
    private int score = 0;
    private boolean firstScreenAlreadyShown = false;
    private boolean progressMarked = false; // żeby nie zapisywać wielokrotnie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showScreen(currentScreen);
        firstScreenAlreadyShown = true;
    }

    private void showScreen(int screenNumber) {
        tts.stop();

        switch (screenNumber) {
            case 1:  setContentView(R.layout.activity_sms_1);         break;
            case 2:  setContentView(R.layout.activity_sms_2);         break;
            case 3:  setContentView(R.layout.activity_sms_3);         break;
            case 4:  setContentView(R.layout.activity_sms_4);         break;
            case 5:  setContentView(R.layout.activity_sms_5);         break;
            case 6:  setContentView(R.layout.activity_sms_6);         break;
            case 7:  setContentView(R.layout.activity_sms_7);         break;
            case 8:  setContentView(R.layout.activity_sms_8);         break;
            case 9:  setContentView(R.layout.activity_sms_9);         break;
            case RESULT_SCREEN:
                setContentView(R.layout.activity_sms_result);
                setupResultUi(); // konfiguracja toolbara, przycisków i tekstu wyniku
                return;          // nie ustawiamy tu przycisków TAK/NIE
        }

        setupButtons();
        setupAnswerButtons();

        if (firstScreenAlreadyShown) {
            getWindow().getDecorView().post(this::speakIfEnabled);
        }
    }

    private void setupAnswerButtons() {
        View yes = findViewById(R.id.yesButton);
        View no  = findViewById(R.id.noButton);
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
            case 2: return !answeredYes;
            case 3: return !answeredYes;
            case 4: return !answeredYes;
            case 5: return answeredYes;
            case 6: return !answeredYes;
            case 7: return answeredYes;
            case 8: return !answeredYes;
            case 9: return answeredYes;
            default: return false;
        }
    }

    private void goNextQuestion(boolean correct) {
        if (currentScreen >= FIRST_QUESTION_SCREEN && currentScreen <= LAST_QUESTION_SCREEN) {
            if (correct) score++;
            Log.d(TAG, "Screen=" + currentScreen + " correct=" + correct + " score=" + score);

            if (currentScreen == LAST_QUESTION_SCREEN) {
                goToResultScreen();
                return;
            }

            currentScreen++;
            showScreen(currentScreen);
        }
    }

    private void goToResultScreen() {
        Log.d(TAG, "FINAL SCORE=" + score);

        // zapis postępu tylko raz
        if (!progressMarked) {
            ProgressStore.markDone(this, ProgressKeys.M1_SMSEMAIL_DONE);
            progressMarked = true;
        }

        currentScreen = RESULT_SCREEN;
        showScreen(currentScreen);
    }

    /**
     * Konfiguracja ekranu wyniku – ten sam design co FakeNewsResultActivity,
     * ale w tym samym Activity.
     */
    private void setupResultUi() {
        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> {
                tts.stop();
                finish();
            });
        }


        ImageView finishMedal = findViewById(R.id.finishMedal);
        TextView resultText   = findViewById(R.id.resultText);
        TextView resultScore  = findViewById(R.id.resultScore);
        MaterialButton retryButton    = findViewById(R.id.retryTestButton);
        MaterialButton backToMenuButton = findViewById(R.id.backToMenuButton);

        int maxScore = LAST_QUESTION_SCREEN - FIRST_QUESTION_SCREEN + 1; // 8
        if (resultScore != null) {
            resultScore.setText("Twój wynik: " + score + "/" + maxScore);
        }

        float percent = score * 100f / maxScore;
        ProfileManager pm = new ProfileManager(this);

        if (finishMedal != null && resultText != null) {
            if (percent >= 80f) {
                finishMedal.setImageResource(R.drawable.ic_medal_gold);
                resultText.setText("BRAWO! Perfekcyjnie ukończyłeś moduł „Bezpieczne wiadomości”.");
            } else if (percent >= 60f) {
                finishMedal.setImageResource(R.drawable.ic_medal_silver);
                resultText.setText("Bardzo dobrze! Kilka drobiazgów do dopracowania, ale świetnie sobie radzisz.");
            } else if (percent >= 40f) {
                finishMedal.setImageResource(R.drawable.ic_medal_bronze);
                resultText.setText("Całkiem nieźle, warto jeszcze trochę poćwiczyć, aby lepiej rozpoznawać zagrożenia.");
            } else {
                finishMedal.setImageResource(R.drawable.ic_sad_emoji);
                resultText.setText("Tym razem się nie udało. Spróbuj jeszcze raz i uważnie czytaj treść wiadomości.");
            }
        }

        if (retryButton != null) {
            retryButton.setOnClickListener(v -> {
                tts.stop();
                score = 0;
                currentScreen = INTRO_SCREEN;
                progressMarked = false;
                showScreen(currentScreen);
            });
        }

        if (backToMenuButton != null) {
            backToMenuButton.setOnClickListener(v -> {
                tts.stop();
                finish();
            });
        }

        // TTS treści wyniku
        getWindow().getDecorView().post(this::speakIfEnabled);
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

        // przyciski retry/back na ekranach starych wyników, jeśli jeszcze są używane
        View retryTestButton = findViewById(R.id.retryTestButton);
        if (retryTestButton != null && currentScreen != RESULT_SCREEN) {
            retryTestButton.setOnClickListener(v -> {
                tts.stop();
                score = 0;
                currentScreen = INTRO_SCREEN;
                progressMarked = false;
                showScreen(currentScreen);
            });
        }

        View backToMenuButton = findViewById(R.id.backToMenuButton);
        if (backToMenuButton != null && currentScreen != RESULT_SCREEN) {
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
        View no  = findViewById(R.id.noButton);

        String yesText = (yes instanceof TextView) ? ((TextView) yes).getText().toString().trim() : "";
        String noText  = (no instanceof TextView)  ? ((TextView) no).getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (!base.isEmpty()) sb.append(base);

        if (!yesText.isEmpty() || !noText.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append("Opcje: ").append(yesText).append(", ").append(noText);
        }

        return sb.toString().trim();
    }
}
