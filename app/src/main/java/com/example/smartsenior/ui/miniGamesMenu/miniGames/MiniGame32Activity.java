package com.example.smartsenior.ui.miniGamesMenu.miniGames;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.example.smartsenior.data.scores.HighScoreStore;
import com.example.smartsenior.data.scores.ScoreKeys;
import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class MiniGame32Activity extends AppCompatActivity {

    private static final int ROUND_SIZE = 4;     // zawsze 4 pytania
    private static final int EXTRA_DECOYS = 1;   // ile dodatkowych “zmyłek” w puli (np. http)

    private HorizontalScrollView topScroll;
    private LinearLayout topChipsContainer;

    private MaterialButton btnBack, btnEvaluate;

    // UI: rekord + aktualny wynik
    private TextView tvHighScore;
    private TextView tvCurrentScore;

    // wiersze definicji
    private final ViewGroup[] answerHosts = new ViewGroup[ROUND_SIZE];
    private final View[] placeholders = new View[ROUND_SIZE];
    private final TextView[] defTexts = new TextView[ROUND_SIZE];

    private boolean evaluated = false;

    // --- NOWE: baza pytań + wylosowana runda ---
    private static class Question {
        final String answer;      // słowo do dopasowania (na chipie)
        final String definition;  // opis

        Question(String answer, String definition) {
            this.answer = answer;
            this.definition = definition;
        }
    }

    private final List<Question> questionBank = new ArrayList<>();
    private final List<Question> roundQuestions = new ArrayList<>(); // wylosowane 4 na rundę

    // pula chipów (odpowiedzi + zmyłki)
    private final List<String> pool = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game3_2);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        topScroll = findViewById(R.id.topScroll);
        topChipsContainer = findViewById(R.id.topChipsContainer);

        btnBack = findViewById(R.id.btnBack);
        btnEvaluate = findViewById(R.id.btnEvaluate);

        tvHighScore = findViewById(R.id.tvHighScore);
        tvCurrentScore = findViewById(R.id.tvCurrentScore);

        updateHighScoreUi();
        tvCurrentScore.setText("Wynik: -/4");

        LinearLayout defsContainer = findViewById(R.id.definitionsContainer);

        for (int i = 0; i < ROUND_SIZE; i++) {
            View row = defsContainer.getChildAt(i);

            ViewGroup host = row.findViewById(R.id.answerHost);
            View placeholder = row.findViewById(R.id.placeholder);
            TextView tvDef = row.findViewById(R.id.tvDefinition);

            answerHosts[i] = host;
            placeholders[i] = placeholder;
            defTexts[i] = tvDef;

            host.setOnDragListener(this::onHostDrag);
        }

        buildQuestionBank();

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MiniGamesMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnEvaluate.setOnClickListener(v -> evaluateAnswers());

        startNewRound();
    }

    private void buildQuestionBank() {
        questionBank.clear();

        questionBank.add(new Question(
                "https",
                "- zaczyna się od tego każdy bezpieczny adres strony internetowej."
        ));

        questionBank.add(new Question(
                "AI",
                "- program komputerowy, mogący generować różne treści, np. zdjęcia, teksty."
        ));

        questionBank.add(new Question(
                "Metoda na wnuczka/policjanta",
                "- oszustwo, w którym przestępca podaje się za osobę bliską lub pracownika ważnej instytucji."
        ));

        questionBank.add(new Question(
                "Fake News",
                "- fałszywe informacje mające na celu wprowadzić zamęt i dezinformację."
        ));

        // --- dodatkowe przykłady (DOPISUJ) ---
        questionBank.add(new Question(
                "Phishing",
                "- próba wyłudzenia danych (np. hasła) przez podszywanie się pod zaufaną instytucję."
        ));

        questionBank.add(new Question(
                "Silne hasło",
                "- hasło długie, trudne do odgadnięcia, z różnymi typami znaków."
        ));

        questionBank.add(new Question(
                "Dwuskładnikowe logowanie (2FA)",
                "- dodatkowy krok przy logowaniu, np. kod SMS lub aplikacja."
        ));

        questionBank.add(new Question(
                "Aktualizacja",
                "- instalowanie poprawek systemu/aplikacji, aby zwiększyć bezpieczeństwo."
        ));

        questionBank.add(new Question(
                "Antywirus",
                "- program, który pomaga wykrywać i usuwać złośliwe oprogramowanie."
        ));

        questionBank.add(new Question(
                "Spam",
                "- niechciane wiadomości, często reklamowe lub podejrzane."
        ));

        questionBank.add(new Question(
                "Link",
                "- odnośnik, który przenosi do innej strony lub zasobu w internecie."
        ));
    }

    private void updateHighScoreUi() {
        int best = HighScoreStore.getHighScore(this, ScoreKeys.MG3_DEFS_HIGH_SCORE);
        tvHighScore.setText("Rekord: " + best + "/4");
    }

    private void startNewRound() {
        evaluated = false;
        tvCurrentScore.setText("Wynik: -/4");

        for (int i = 0; i < answerHosts.length; i++) {
            ViewGroup host = answerHosts[i];
            host.removeAllViews();
            host.addView(placeholders[i]);
        }

        topChipsContainer.removeAllViews();

        roundQuestions.clear();
        List<Question> copy = new ArrayList<>(questionBank);
        Collections.shuffle(copy);

        int take = Math.min(ROUND_SIZE, copy.size());
        for (int i = 0; i < take; i++) roundQuestions.add(copy.get(i));

        for (int i = 0; i < ROUND_SIZE; i++) {
            if (i < roundQuestions.size()) {
                defTexts[i].setText(roundQuestions.get(i).definition);
            } else {
                defTexts[i].setText("- (brak pytania w bazie)");
            }
        }

        pool.clear();
        HashSet<String> used = new HashSet<>();

        for (Question q : roundQuestions) {
            pool.add(q.answer);
            used.add(q.answer);
        }

        String[] decoys = new String[]{"http", "WWW", "SMS", "1234", "Admin"};

        int added = 0;
        List<String> decoyList = new ArrayList<>();
        Collections.addAll(decoyList, decoys);
        Collections.shuffle(decoyList);

        for (String d : decoyList) {
            if (added >= EXTRA_DECOYS) break;
            if (!used.contains(d)) {
                pool.add(d);
                used.add(d);
                added++;
            }
        }

        Collections.shuffle(pool);

        for (String word : pool) {
            topChipsContainer.addView(createChip(word));
        }
    }

    private View createChip(String word) {
        View chip = LayoutInflater.from(this).inflate(R.layout.item_draggable_chip, topChipsContainer, false);
        TextView tv = chip.findViewById(R.id.tvChip);
        tv.setText(word);

        chip.setTag(word);
        resetChipColor(chip);

        chip.setOnClickListener(v -> {
            if (evaluated) return;

            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null && parent != topChipsContainer) {
                int index = findHostIndex(parent);
                parent.removeView(v);

                LinearLayout.LayoutParams topLp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                v.setLayoutParams(topLp);

                topChipsContainer.addView(v);
                resetChipColor(v);

                restorePlaceholder(index);
            }
        });

        chip.setOnLongClickListener(v -> {
            if (evaluated) return true;
            if (topScroll != null) topScroll.requestDisallowInterceptTouchEvent(true);

            String label = (String) v.getTag();
            ClipData data = new ClipData(
                    label,
                    new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                    new ClipData.Item(label)
            );

            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadow, v, 0);
            return true;
        });

        return chip;
    }

    private boolean onHostDrag(View target, DragEvent event) {
        ViewGroup host = (ViewGroup) target;

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return event.getClipDescription() != null
                        && event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);

            case DragEvent.ACTION_DROP: {
                if (evaluated) return true;

                View dragged = (View) event.getLocalState();
                if (dragged == null) return true;

                View existingChip = findChipInHost(host);
                if (existingChip != null) {
                    host.removeView(existingChip);
                    topChipsContainer.addView(existingChip);
                    resetChipColor(existingChip);

                    LinearLayout.LayoutParams topLp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    existingChip.setLayoutParams(topLp);
                }

                ViewGroup oldParent = (ViewGroup) dragged.getParent();
                if (oldParent != null) oldParent.removeView(dragged);

                host.removeAllViews();
                host.addView(dragged);

                android.widget.FrameLayout.LayoutParams hostLp =
                        new android.widget.FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        );
                hostLp.gravity = android.view.Gravity.CENTER;
                dragged.setLayoutParams(hostLp);

                resetChipColor(dragged);
                return true;
            }

            case DragEvent.ACTION_DRAG_ENDED:
                if (topScroll != null) topScroll.requestDisallowInterceptTouchEvent(false);
                return true;
        }

        return true;
    }

    private View findChipInHost(ViewGroup host) {
        for (int i = 0; i < host.getChildCount(); i++) {
            View child = host.getChildAt(i);
            if (child.getId() != R.id.placeholder) return child;
        }
        return null;
    }

    private int findHostIndex(ViewGroup host) {
        for (int i = 0; i < answerHosts.length; i++) {
            if (answerHosts[i] == host) return i;
        }
        return -1;
    }

    private void restorePlaceholder(int hostIndex) {
        if (hostIndex < 0 || hostIndex >= answerHosts.length) return;

        ViewGroup host = answerHosts[hostIndex];
        if (findChipInHost(host) == null) {
            host.removeAllViews();
            host.addView(placeholders[hostIndex]);
        }
    }

    private void evaluateAnswers() {
        evaluated = true;

        int good = 0;

        for (int i = 0; i < ROUND_SIZE; i++) {
            ViewGroup host = answerHosts[i];
            View chip = findChipInHost(host);
            if (chip == null) continue;

            String word = (String) chip.getTag();

            String correctAnswer = (i < roundQuestions.size()) ? roundQuestions.get(i).answer : null;

            if (correctAnswer != null && correctAnswer.equals(word)) {
                setChipGreen(chip);
                good++;
            } else {
                setChipRed(chip);
            }
        }

        tvCurrentScore.setText("Wynik: " + good + "/4");

        boolean newRecord = HighScoreStore.submitHighScore(this, ScoreKeys.MG3_DEFS_HIGH_SCORE, good);
        updateHighScoreUi();

        int best = HighScoreStore.getHighScore(this, ScoreKeys.MG3_DEFS_HIGH_SCORE);

        String msg = "Poprawne odpowiedzi: " + good + "/4\n"
                + "Rekord: " + best + "/4"
                + (newRecord ? "\n\nNowy rekord!" : "");

        new AlertDialog.Builder(this)
                .setTitle("Wynik")
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }

    private void setChipGreen(View chip) {
        if (chip instanceof MaterialCardView) {
            ((MaterialCardView) chip).setCardBackgroundColor(0xFFB7F5C9);
        } else {
            chip.setBackgroundColor(0xFFB7F5C9);
        }
    }

    private void setChipRed(View chip) {
        if (chip instanceof MaterialCardView) {
            ((MaterialCardView) chip).setCardBackgroundColor(0xFFF7B3B3);
        } else {
            chip.setBackgroundColor(0xFFF7B3B3);
        }
    }

    private void resetChipColor(View chip) {
        if (chip instanceof MaterialCardView) {
            ((MaterialCardView) chip).setCardBackgroundColor(0xFFF6F1B5); // żółty
        }
    }
}

