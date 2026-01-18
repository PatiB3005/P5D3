package com.example.smartsenior.ui.moduleMenu.modules.aiLite.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.smartsenior.R;
import com.example.smartsenior.data.ResultPopup;
import com.example.smartsenior.data.progress.ProgressKeys;
import com.example.smartsenior.data.progress.ProgressStore;
import com.example.smartsenior.ui.BaseTTSActivity;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class AiQuizActivity extends BaseTTSActivity {

    private static class QuizItem {
        boolean isAI;
        String explanation;

        QuizItem(boolean isAI, String explanation) {
            this.isAI = isAI;
            this.explanation = explanation;
        }
    }

    private MaterialButton btnAI, btnReal, btnNext;
    private ResultPopup resultPopup;

    private final List<QuizItem> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int correctCount = 0;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initQuestions();
        showCurrentQuestion();
    }

    private void initQuestions() {
        questions.add(new QuizItem(true,
                "To zdjęcie ma elementy wskazujące, że zostało wygenerowane przez sztuczną inteligencję. "
                        + "Nierealistyczna scenografia – taka scena nie miałaby racji bytu. "
                        + "Nienaturalne oświetlenie – na tak dużym obiekcie światło zwykle tworzy bardziej złożone cienie. "
                        + "AI często daje ładne, ale trochę płaskie i równomierne światło na całym obiekcie. "
                        + "Zbyt równomierny sezam – AI nie umie wygenerować losowo rozrzuconego sezamu, tylko robi to sekwencyjnie. "
                        + "Zbyt idealne przejścia – granice między elementami są zaskakująco czyste, jakby wyrzeźbione. "
                        + "W prawdziwej fotografii widać zwykle mikro‑niedoskonałości: okruchy, pęknięcia, krzywe krawędzie, nierówne przypieczenia."));

        questions.add(new QuizItem(false,
                "To zdjęcie jest prawdziwe, ponieważ ma naturalną strukturę futra, wąsów i oczu. "
                        + "Losowa, drobna struktura futra – włoski mają naturalny chaos: różne długości, kierunki, gęstość i mikro‑cienie. "
                        + "AI często robi futro zbyt jednolite albo z powtarzalnym wzorem. "
                        + "Wąsy są cienkie i nieregularne – mają różną grubość, lekkie załamania i zanikają w tle, nie wyglądają jak dorzucone linie. "
                        + "Oczy mają realistyczne refleksy – w tęczówce i źrenicy widać naturalne odbicie światła i głębię. "
                        + "AI bywa niespójne z odbiciami i kształtem źrenic. "
                        + "Spójne światło i cienie na pysku – rozkład jasnych i ciemnych miejsc na nosie, policzku i pod okiem wygląda jak z aparatu, bez dziwnych załamań."));

        questions.add(new QuizItem(true,
                "To zdjęcie wygląda na wygenerowane przez sztuczną inteligencję. "
                        + "Nierealistyczna scena – całość przypomina plakat albo wizualizację, a nie naturalne ujęcie. "
                        + "Problemy z anatomią – dłonie, palce lub proporcje ciała mogą wyglądać nienaturalnie. "
                        + "Zbyt gładkie powierzchnie – materiały i skóra są mocno wygładzone, jakby przefiltrowane. "
                        + "Światło jest bardzo równe i katalogowe, z małą liczbą drobnych cieni i niedoskonałości. "
                        + "Takie połączenie elementów jest typowe dla obrazów generowanych przez modele AI."));

        questions.add(new QuizItem(true,
                "To zdjęcie ma wiele cech obrazu wygenerowanego przez sztuczną inteligencję. "
                        + "Nierealistyczne fałdy materiału – spódnica ma idealnie gładkie, malarskie przejścia cieni i bardzo równe, szerokie fale. "
                        + "W prawdziwej tkaninie widać zwykle więcej drobnych zagnieceń i mniej perfekcyjnie równych gradientów. "
                        + "Postać jest niekompletna – u góry brakuje szyi i głowy, a u dołu widać tylko jedną nogę albo ciało jest ucięte w nienaturalnym miejscu, co bardzo często zdarza się AI. "
                        + "Podejrzanie spójne światło – cała sukienka, sofa i dodatki mają bardzo katalogowe, równe oświetlenie bez drobnych niedoskonałości i cieni kontaktowych typowych dla prawdziwego zdjęcia. "
                        + "Scena jest zbyt katalogowa – wszystko jest perfekcyjnie czyste i idealne, jakby z reklamy."));

        questions.add(new QuizItem(false,
                "To zdjęcie jest prawdziwe. "
                        + "Naturalna nieidealność pączków – każdy ma odrobinę inny kształt, inną ilość lukru i inną szerokość jasnej obwódki. "
                        + "AI często tworzy rzeczy zbyt równe albo z powtarzalnym wzorem. "
                        + "Lukier zachowuje się realistycznie – widać nierówne zacieki, prześwity i różną grubość warstwy; tam, gdzie jest cień, lukier wygląda inaczej. "
                        + "To fizycznie złożone i trudniejsze do wiarygodnego odwzorowania dla modeli AI. "
                        + "Nieregularne rozmieszczenie posypki – drobinki są rozmieszczone chaotycznie, a nie w równym, sekwencyjnym wzorze. "
                        + "Spójne światło i cienie – kierunek światła jest konsekwentny na wszystkich pączkach i na talerzu, a cienie kontaktowe w miejscach styku z tacą wyglądają naturalnie."));
    }

    private void showCurrentQuestion() {
        QuizItem item = questions.get(currentIndex);

        int layoutId = getLayoutForQuestion(currentIndex);
        setContentView(layoutId);

        resultPopup = new ResultPopup(this);

        btnAI = findViewById(R.id.btnFake);
        btnReal = findViewById(R.id.btnReal);
        btnNext = findViewById(R.id.btnNext);

        if (btnNext != null) btnNext.setVisibility(View.GONE);
        answered = false;
        resetButtons();

        if (btnAI != null) btnAI.setOnClickListener(v -> onAnswer(true));
        if (btnReal != null) btnReal.setOnClickListener(v -> onAnswer(false));
        if (btnNext != null) btnNext.setOnClickListener(v -> goNext());
    }

    private void resetButtons() {
        int blue = Color.parseColor("#2B6CB0");
        if (btnAI != null) btnAI.setBackgroundColor(blue);
        if (btnReal != null) btnReal.setBackgroundColor(blue);
    }

    private int getLayoutForQuestion(int questionIndex) {
        switch (questionIndex) {
            case 0:
                return R.layout.activity_ailite_photo1;
            case 1:
                return R.layout.activity_ailite_photo2;
            case 2:
                return R.layout.activity_ailite_photo3;
            case 3:
                return R.layout.activity_ailite_photo4;
            case 4:
                return R.layout.activity_ailite_photo5;
            default:
                return R.layout.activity_ailite_photo1;
        }
    }

    private void onAnswer(boolean selectedAI) {
        if (answered) return;
        answered = true;

        QuizItem item = questions.get(currentIndex);
        boolean correct = (selectedAI == item.isAI);

        if (correct) correctCount++;

        MaterialButton selected = selectedAI ? btnAI : btnReal;
        MaterialButton correctBtn = item.isAI ? btnAI : btnReal;

        if (correct) {
            selected.setBackgroundColor(Color.parseColor("#A7F3D0"));
        } else {
            selected.setBackgroundColor(Color.parseColor("#FECACA"));
            correctBtn.setBackgroundColor(Color.parseColor("#A7F3D0"));
        }

        resultPopup.setOnDismissListener(() -> {
            if (btnNext != null) btnNext.setVisibility(View.VISIBLE);
        });
        resultPopup.show(correct, item.explanation, "OK");
    }

    private void goNext() {
        if (currentIndex == questions.size() - 1) {
            ProgressStore.markDone(this, ProgressKeys.AI_QUIZ_DONE);
            Intent intent = new Intent(this, AiResultActivity.class);
            intent.putExtra("score", correctCount);
            intent.putExtra("maxScore", questions.size());
            startActivity(intent);
            finish();
        } else {
            currentIndex++;
            showCurrentQuestion();
        }
    }

    @Override
    protected String getSpeakText() {
        return "Pytanie " + (currentIndex + 1) + " z " + questions.size() + ". Czy to AI czy prawdziwe zdjęcie?";
    }
}