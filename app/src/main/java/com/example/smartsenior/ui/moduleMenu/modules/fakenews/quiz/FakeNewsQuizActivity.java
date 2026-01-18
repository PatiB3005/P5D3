package com.example.smartsenior.ui.moduleMenu.modules.fakenews.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import com.example.smartsenior.R;
import com.example.smartsenior.data.ResultPopup;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;


import java.util.ArrayList;
import java.util.List;

public class FakeNewsQuizActivity extends BaseTTSActivity {

    private static class QuizItem {
        String headline;
        boolean isTrue;
        String explanation;

        QuizItem(String headline, boolean isTrue, String explanation) {
            this.headline = headline;
            this.isTrue = isTrue;
            this.explanation = explanation;
        }
    }

    private TextView headlineText;
    private MaterialCardView cardHeadline;
    private Button yesButton;
    private Button noButton;

    private final List<QuizItem> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int correctCount = 0;
    private boolean answered = false;


    private boolean hasResumed = false;

    private ResultPopup resultPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_quiz);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            tts.stop();
            finish();
        });

        // Inicjalizacja widoków
        headlineText = findViewById(R.id.headlineText);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);

        // Popup initialization
        resultPopup = new ResultPopup(this);

        // Preparing questions and displaying the first one
        initQuestions();
        showCurrentQuestion();

        // Setting the listeners to buttons
        setupOption(yesButton, true);
        setupOption(noButton, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasResumed = true;
    }

    @Override
    protected String getSpeakText() {
        String h = (headlineText != null && headlineText.getText() != null) ? headlineText.getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (!h.isEmpty()) sb.append(h);

        return sb.toString().trim();
    }

    private void initQuestions() {
        questions.clear();

        questions.add(new QuizItem(
                "Ministerstwo Zdrowia potwierdziło: od 2026 roku szczepionka przeciw grypie będzie obowiązkowa dla wszystkich dorosłych",
                false,
                "To FAŁSZ. Ministerstwo nie planuje obowiązkowych szczepień przeciw grypie dla wszystkich dorosłych. Są tylko zachęty, szczególnie dla seniorów."
        ));

        questions.add(new QuizItem(
                "Donald Tusk w tajemnicy spotkał się z Putinem w Dubaju – wyciekły zdjęcia",
                false,
                "To FAŁSZ. To typowy rosyjski fake news. „Dowody” to przerobione lub stare zdjęcia, które nie mają nic wspólnego z takim spotkaniem."
        ));

        questions.add(new QuizItem(
                "Inflacja w listopadzie spadła do 1,8% – najniżej od 10 lat! GUS podał najnowsze dane",
                true,
                "To PRAWDA. GUS podał inflację na poziomie 1,8% – to najniższy wynik od wielu lat."
        ));

        questions.add(new QuizItem(
                "Lidl i Biedronka wycofują wszystkie jajka z chowu klatkowego już od stycznia 2026",
                false,
                "To FAŁSZ. Sklepy zapowiadają stopniowe zmiany, ale nie ma decyzji o nagłym wycofaniu wszystkich takich jajek od jednego miesiąca."
        ));

        questions.add(new QuizItem(
                "Rząd wprowadza 800+ już od marca 2026 – jest oficjalny projekt ustawy",
                false,
                "To FAŁSZ. Były takie obietnice w kampanii, ale nie ma oficjalnego projektu ustawy w Sejmie."
        ));

        questions.add(new QuizItem(
                "Lewandowski oficjalnie wraca do reprezentacji Polski na Mundial 2026 – potwierdził PZPN",
                false,
                "To FAŁSZ. Robert Lewandowski ogłosił zakończenie gry w reprezentacji i nie ma potwierdzenia, że wraca."
        ));

        questions.add(new QuizItem(
                "PKP Intercity wprowadza całkowity zakaz sprzedaży alkoholu we wszystkich pociągach od 1 stycznia",
                false,
                "To FAŁSZ. Ograniczenia dotyczą głównie mocnego alkoholu. Informacja o całkowitym zakazie jest przesadzona."
        ));

        questions.add(new QuizItem(
                "Sejm przyjął ustawę o zakazie sprzedaży energii Rosji i Białorusi – Polska odcięta od wschodniego prądu od 2026",
                true,
                "To PRAWDA. Ustawa zakłada, że od 2026 roku energia z Rosji i Białorusi nie będzie sprzedawana w Polsce."
        ));
    }

    private void showCurrentQuestion() {
        QuizItem item = questions.get(currentIndex);
        headlineText.setText(item.headline);
        resetCardsVisual();
        answered = false;

        if (hasResumed && tts.isEnabled()) {
            tts.stop();
            tts.speak(getSpeakText());
        }
    }

    private void resetCardsVisual() {
        int defaultColor = Color.parseColor("#2B6CB0");
        yesButton.setBackgroundColor(defaultColor);
        noButton.setBackgroundColor(defaultColor);
    }

    private void setupOption(Button btn, boolean answerValue) {
        btn.setOnClickListener(v -> {
            if (answered) return;
            answered = true;

            tts.stop();

            QuizItem item = questions.get(currentIndex);
            boolean isCorrect = (item.isTrue == answerValue);

            if (isCorrect) {
                correctCount++;
                btn.setBackgroundColor(Color.parseColor("#A7F3D0")); // Zielony
            } else {
                btn.setBackgroundColor(Color.parseColor("#FECACA")); // Czerwony
            }

            boolean isLast = currentIndex == questions.size() - 1;

            resultPopup.setOnDismissListener(() -> {
                if (isLast) {
                    int maxScore = questions.size();

                    ProgressStore.markDone(this, ProgressKeys.FN_QUIZ_DONE);

                    Intent intent = new Intent(FakeNewsQuizActivity.this, FakeNewsResultActivity.class);
                    intent.putExtra("score", correctCount);
                    intent.putExtra("maxScore", maxScore);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    currentIndex++;
                    showCurrentQuestion();
                }
            });

            resultPopup.show(isCorrect, item.explanation, "OK");
        });
    }

    private void showSummaryDialog() {
        int maxScore = questions.size();

        String message = "Ukończyłeś quiz.\n\n"
                + "Poprawnych odpowiedzi: " + correctCount + " z " + maxScore + ".\n\n"
                + "Pamiętaj: nawet jeśli coś wygląda jak \"sensacyjna bomba\", warto sprawdzić źródło.";

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Podsumowanie")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Zobacz wynik", (dialog, which) -> {
                    dialog.dismiss();

                    // Quiz end
                    ProgressStore.markDone(this, ProgressKeys.FN_QUIZ_DONE);

                    tts.stop();
                    // Score
                    Intent intent = new Intent(FakeNewsQuizActivity.this, FakeNewsResultActivity.class);
                    intent.putExtra("score", correctCount);
                    intent.putExtra("maxScore", maxScore);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
