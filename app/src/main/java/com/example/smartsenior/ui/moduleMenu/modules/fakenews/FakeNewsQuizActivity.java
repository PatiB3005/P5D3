package com.example.smartsenior.ui.moduleMenu.modules.fakenews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.smartsenior.R;
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
    private TextView feedbackText;
    private MaterialCardView cardHeadline;
    private MaterialCardView cardYes;
    private MaterialCardView cardNo;

    private final List<QuizItem> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int correctCount = 0;
    private boolean answered = false;

    // żeby nie dublować odczytu: onResume już powie raz, a kolejne pytania mówimy ręcznie
    private boolean hasResumed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_news_quiz);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> {
            tts.stop();
            finish();
        });

        headlineText = findViewById(R.id.headlineText);
        feedbackText = findViewById(R.id.feedbackText);
        cardHeadline = findViewById(R.id.cardHeadline);
        cardYes = findViewById(R.id.cardYes);
        cardNo = findViewById(R.id.cardNo);

        initQuestions();
        showCurrentQuestion();

        setupOption(cardYes, true);
        setupOption(cardNo, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasResumed = true;
    }

    @Override
    protected String getSpeakText() {
        // Czytamy bieżący nagłówek i instrukcję (najważniejsze elementy quizu)
        String h = (headlineText != null && headlineText.getText() != null) ? headlineText.getText().toString().trim() : "";
        String f = (feedbackText != null && feedbackText.getText() != null) ? feedbackText.getText().toString().trim() : "";

        StringBuilder sb = new StringBuilder();
        if (!h.isEmpty()) sb.append(h);
        if (!f.isEmpty()) {
            if (sb.length() > 0) sb.append(". ");
            sb.append(f);
        }
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
        feedbackText.setText("Dotknij TAK lub NIE, aby sprawdzić odpowiedź.");
        resetCardsVisual();
        answered = false;

        // Zmiana pytania nie powoduje onResume, więc czytamy ręcznie (po pierwszym wejściu).
        if (hasResumed && tts.isEnabled()) {
            tts.stop();
            tts.speak(getSpeakText());
        }
    }

    private void resetCardsVisual() {
        int defaultColor = Color.parseColor("#E5E7EB");
        cardYes.setCardBackgroundColor(defaultColor);
        cardYes.setStrokeWidth(0);
        cardNo.setCardBackgroundColor(defaultColor);
        cardNo.setStrokeWidth(0);
    }

    private void setupOption(MaterialCardView card, boolean answerValue) {
        card.setOnClickListener(v -> {
            if (answered) return;
            answered = true;

            // przerywamy lektora, żeby nie nakładał się na interakcję
            tts.stop();

            QuizItem item = questions.get(currentIndex);
            boolean isCorrect = (item.isTrue == answerValue);

            if (isCorrect) {
                correctCount++;
                card.setCardBackgroundColor(Color.parseColor("#A7F3D0"));
                card.setStrokeColor(Color.parseColor("#059669"));
                card.setStrokeWidth(6);
            } else {
                card.setCardBackgroundColor(Color.parseColor("#FECACA"));
                card.setStrokeColor(Color.parseColor("#DC2626"));
                card.setStrokeWidth(6);
            }

            showExplanationDialog(isCorrect, item.explanation);
        });
    }

    private void showExplanationDialog(boolean isCorrect, String explanation) {
        String title = isCorrect ? "Dobra odpowiedź" : "Niepoprawna odpowiedź";
        boolean isLast = (currentIndex == questions.size() - 1);
        String buttonText = isLast ? "Zakończ quiz" : "Następne pytanie";

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(explanation)
                .setCancelable(false)
                .setPositiveButton(buttonText, (dialog, which) -> {
                    dialog.dismiss();
                    if (isLast) {
                        showSummaryDialog();
                    } else {
                        currentIndex++;
                        showCurrentQuestion();
                    }
                })
                .show();
    }

    private void showSummaryDialog() {
        int maxScore = questions.size();

        String message = "Ukończyłeś quiz.\n\n"
                + "Poprawnych odpowiedzi: " + correctCount + " z " + maxScore + ".\n\n"
                + "Pamiętaj: nawet jeśli coś wygląda jak „sensacyjna bomba”, warto sprawdzić źródło.";

        new AlertDialog.Builder(this)
                .setTitle("Podsumowanie")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Zobacz wynik", (dialog, which) -> {
                    dialog.dismiss();

                    ProgressStore.markDone(this, ProgressKeys.FN_QUIZ_DONE);

                    tts.stop();
                    Intent intent = new Intent(FakeNewsQuizActivity.this, FakeNewsResultActivity.class);
                    intent.putExtra("score", correctCount);
                    intent.putExtra("maxScore", maxScore);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .show();
    }
}
