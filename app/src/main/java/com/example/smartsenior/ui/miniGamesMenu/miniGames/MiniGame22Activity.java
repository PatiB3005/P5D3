package com.example.smartsenior.ui.miniGamesMenu.miniGames;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MiniGame22Activity extends AppCompatActivity implements TileAdapter.OnTileClickListener {

    // UI
    private RecyclerView rvTiles;
    private LinearLayout chipsContainer;
    private LinearProgressIndicator progressStrength;
    private TextView tvStrength;

    private MaterialButton btnBack, btnEvaluate, btnRepeat, btnFinish;
    private View spaceBackEvaluate, spaceRepeatFinish;

    // Data
    private final List<TileItem> availableTiles = new ArrayList<>(); // dół (dynamiczne)
    private final List<TileItem> selectedTiles = new ArrayList<>();  // żółte pole (kolejność dodania)
    private TileAdapter adapter;

    private final Random rng = new Random();

    // --- PULE DO LOSOWANIA ---
    private static final String[] WORDS = {
            "Kawa","Las","Rower","Kot","Wiosna","Lato","Jesien","Zima",
            "Kubek","Koc","Lampa","Pilot","Mapa","Parasol","Gazeta","Notes",
            "Morze","Rzeka","Chmura","Kwiat","Trawa","Kamien","Gwiazda","Wiatr",
            "Zielony","Niebieski","Zloty","Cichy","Mily","Jasny","Spokojny","Mocny",
            "Telefon","Zdjecie","Radio","Szachy","Puzzle","Czytanie","Muzyka","Spacer"
    };

    private static final String[] NUMBERS = {
            "7","9","13","18","21","27","33","42","58","69","81","94",
            "105","209","318","404","517","604","728","861"
    };

    private static final String[] SYMBOLS = { "!", "?", "_", "-", "@", "#", "*" };
    private static final String[] SEPARATORS = { "_", "-" };

    // edukacyjne “złe” (opcjonalnie)
    private static final String[] BAD = { "123", "abcd", "haslo", "qwerty" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game2_2);

        // bind
        rvTiles = findViewById(R.id.rvTiles);
        chipsContainer = findViewById(R.id.passwordChipsContainer);
        progressStrength = findViewById(R.id.progressStrength);
        tvStrength = findViewById(R.id.tvStrength);

        btnBack = findViewById(R.id.btnBack);
        btnEvaluate = findViewById(R.id.btnEvaluate);
        btnRepeat = findViewById(R.id.btnRepeat);
        btnFinish = findViewById(R.id.btnFinish);

        spaceBackEvaluate = findViewById(R.id.spaceBackEvaluate);
        spaceRepeatFinish = findViewById(R.id.spaceRepeatFinish);

        // recycler
        rvTiles.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new TileAdapter(availableTiles, this);
        rvTiles.setAdapter(adapter);

        // start
        resetGame();

        // listeners
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MiniGamesMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });


        btnEvaluate.setOnClickListener(v -> {
            if (selectedTiles.isEmpty()) return;

            String pwd = buildPasswordStringFromSelected();
            PasswordEvaluator.EvaluationResult result = PasswordEvaluator.evaluate(pwd);
            applyStrengthToUi(result);
            showResultDialog(result);

            // po ocenie zamieniamy przyciski
            showResultModeButtons();
        });

        btnRepeat.setOnClickListener(v -> resetGame());

        btnFinish.setOnClickListener(v -> {
            Intent intent = new Intent(this, MiniGamesMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    // ============ TRYBY DOLNEGO PASKA ============

    private void showEvaluateModeButtons() {
        btnBack.setVisibility(View.VISIBLE);
        btnEvaluate.setVisibility(View.VISIBLE);
        if (spaceBackEvaluate != null) spaceBackEvaluate.setVisibility(View.VISIBLE);

        btnRepeat.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
        if (spaceRepeatFinish != null) spaceRepeatFinish.setVisibility(View.GONE);
    }

    private void showResultModeButtons() {
        btnBack.setVisibility(View.GONE);
        btnEvaluate.setVisibility(View.GONE);
        if (spaceBackEvaluate != null) spaceBackEvaluate.setVisibility(View.GONE);

        btnRepeat.setVisibility(View.VISIBLE);
        btnFinish.setVisibility(View.VISIBLE);
        if (spaceRepeatFinish != null) spaceRepeatFinish.setVisibility(View.VISIBLE);
    }

    private boolean isResultMode() {
        return btnRepeat.getVisibility() == View.VISIBLE;
    }

    // ============ LOSOWANIE KAFELKÓW ============

    private void setupTilesRandom() {
        availableTiles.clear();

        final int TOTAL = 16;     // siatka 4x4
        final int WORD_COUNT = 10;
        final int NUM_COUNT = 3;
        final int SYM_COUNT = 2;  // żeby zawsze dało się mieć znak specjalny
        final int BAD_COUNT = 1;  // możesz ustawić 0 jeśli nie chcesz

        HashSet<String> usedWords = new HashSet<>();
        HashSet<String> usedNums = new HashSet<>();
        HashSet<String> usedSyms = new HashSet<>();
        HashSet<String> usedBad = new HashSet<>();

        // słowa
        for (int i = 0; i < WORD_COUNT; i++) {
            String w = pickUnique(WORDS, usedWords);
            availableTiles.add(new TileItem(w, TileItem.Type.WORD, availableTiles.size()));
        }

        // liczby
        for (int i = 0; i < NUM_COUNT; i++) {
            String n = pickUnique(NUMBERS, usedNums);
            availableTiles.add(new TileItem(n, TileItem.Type.NUMBER, availableTiles.size()));
        }

        // symbole
        for (int i = 0; i < SYM_COUNT; i++) {
            String s = pickUnique(SYMBOLS, usedSyms);
            availableTiles.add(new TileItem(s, TileItem.Type.SYMBOL, availableTiles.size()));
        }

        // złe (opcjonalnie)
        for (int i = 0; i < BAD_COUNT; i++) {
            String b = pickUnique(BAD, usedBad);
            availableTiles.add(new TileItem(b, TileItem.Type.BAD, availableTiles.size()));
        }

        // dopełnij (separatorami albo dodatkowymi słowami)
        while (availableTiles.size() < TOTAL) {
            String extra = pickUnique(SEPARATORS, usedSyms);
            availableTiles.add(new TileItem(extra, TileItem.Type.SEPARATOR, availableTiles.size()));
        }

        // losowa kolejność na dole
        Collections.shuffle(availableTiles, rng);

        // po shuffle ustawiamy originalIndex = aktualna pozycja (żeby wracało “na miejsce”)
        for (int i = 0; i < availableTiles.size(); i++) {
            TileItem old = availableTiles.get(i);
            availableTiles.set(i, new TileItem(old.text, old.type, i));
        }
    }

    private String pickUnique(String[] pool, HashSet<String> used) {
        for (int tries = 0; tries < 50; tries++) {
            String v = pool[rng.nextInt(pool.length)];
            if (!used.contains(v)) {
                used.add(v);
                return v;
            }
        }
        return pool[rng.nextInt(pool.length)];
    }

    // ============ MECHANIKA: dół <-> żółte ============

    @Override
    public void onTileClick(TileItem item) {
        if (isResultMode()) return; // po ocenie blokujemy edycję

        // usuń z dołu
        int pos = adapter.indexOf(item);
        if (pos >= 0) adapter.removeAt(pos);

        // dodaj do żółtego
        selectedTiles.add(item);
        addChipView(item);

        recalcAndUpdateUi();
    }

    private void addChipView(TileItem item) {
        View chip = LayoutInflater.from(this).inflate(R.layout.item_password_chip, chipsContainer, false);
        TextView tvChip = chip.findViewById(R.id.tvChip);
        tvChip.setText(item.text);

        chip.setTag(item);
        chip.setOnClickListener(v -> {
            if (isResultMode()) return; // po ocenie blokujemy edycję
            removeChipAndReturnTile((TileItem) v.getTag(), v);
        });

        chipsContainer.addView(chip);
    }

    private void removeChipAndReturnTile(TileItem item, View chipView) {
        selectedTiles.remove(item);
        chipsContainer.removeView(chipView);

        int insertPos = findInsertPositionByOriginalIndex(item.originalIndex);
        adapter.insertAt(insertPos, item);

        recalcAndUpdateUi();
    }

    private int findInsertPositionByOriginalIndex(int originalIndex) {
        for (int i = 0; i < availableTiles.size(); i++) {
            if (availableTiles.get(i).originalIndex > originalIndex) return i;
        }
        return availableTiles.size();
    }

    private void undoLastPart() {
        if (selectedTiles.isEmpty() || isResultMode()) return;

        TileItem last = selectedTiles.get(selectedTiles.size() - 1);
        selectedTiles.remove(selectedTiles.size() - 1);

        int childCount = chipsContainer.getChildCount();
        if (childCount > 0) chipsContainer.removeViewAt(childCount - 1);

        int insertPos = findInsertPositionByOriginalIndex(last.originalIndex);
        adapter.insertAt(insertPos, last);

        recalcAndUpdateUi();
    }

    // ============ LICZENIE MOCY “NA ŻYWO” ============

    private void recalcAndUpdateUi() {
        boolean hasAny = !selectedTiles.isEmpty();

        btnBack.setEnabled(hasAny);
        btnEvaluate.setEnabled(hasAny);

        if (!hasAny) {
            progressStrength.setProgress(0);
            tvStrength.setText("Moc hasła: 0%");
            return;
        }

        String pwd = buildPasswordStringFromSelected();
        PasswordEvaluator.EvaluationResult result = PasswordEvaluator.evaluate(pwd);
        applyStrengthToUi(result);
    }

    private String buildPasswordStringFromSelected() {
        StringBuilder sb = new StringBuilder();
        for (TileItem t : selectedTiles) sb.append(t.text);
        return sb.toString();
    }

    private void applyStrengthToUi(PasswordEvaluator.EvaluationResult result) {
        int percent = Math.max(0, Math.min(100, result.score * 10));
        progressStrength.setProgress(percent);

        String label;
        switch (result.level) {
            case GOOD:
                label = "Moc hasła: " + percent + "% (Silne)";
                break;
            case MEDIUM:
                label = "Moc hasła: " + percent + "% (Średnie)";
                break;
            default:
                label = "Moc hasła: " + percent + "% (Słabe)";
                break;
        }
        tvStrength.setText(label);
    }

    private void showResultDialog(PasswordEvaluator.EvaluationResult result) {
        StringBuilder msg = new StringBuilder();
        msg.append("Twoje hasło: ").append(result.password).append("\n\n");
        msg.append("Wynik: ").append(result.score * 10).append("%\n\n");

        for (String r : result.reasons) {
            msg.append("• ").append(r).append("\n");
        }
        if (result.suggestion != null) {
            msg.append("\nSugestia: ").append(result.suggestion);
        }

        new AlertDialog.Builder(this)
                .setTitle("Ocena hasła")
                .setMessage(msg.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    // ============ RESET GRY ============

    private void resetGame() {
        // tryb edycji
        showEvaluateModeButtons();

        // wyczyść żółte pole
        chipsContainer.removeAllViews();
        selectedTiles.clear();

        // wylosuj nowy zestaw kafelków
        setupTilesRandom();
        if (adapter != null) adapter.notifyDataSetChanged();

        // pasek 0%
        progressStrength.setProgress(0);
        tvStrength.setText("Moc hasła: 0%");

        // przyciski wyłączone dopóki nic nie dodasz
        btnBack.setEnabled(false);
        btnEvaluate.setEnabled(false);
    }
}
