package com.example.smartsenior.ui.miniGamesMenu.miniGames;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsenior.R;
import com.example.smartsenior.ui.miniGamesMenu.MiniGamesMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;

public class MiniGame22Activity extends AppCompatActivity implements TileAdapter.OnTileClickListener {

    // UI
    private RecyclerView rvTiles;
    private LinearLayout chipsContainer;
    private LinearProgressIndicator progressStrength;
    private TextView tvStrength;

    private MaterialButton btnBack, btnEvaluate, btnRepeat, btnFinish;
    private View spaceBackEvaluate, spaceRepeatFinish;

    // Data
    private final List<TileItem> availableTiles = new ArrayList<>();
    private final List<TileItem> selectedTiles = new ArrayList<>();
    private TileAdapter adapter;

    private final Random rng = new Random();

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
    private static final String[] BAD = { "123", "abcd", "haslo", "qwerty" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game2_2);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

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

        FlexboxLayoutManager lm = new FlexboxLayoutManager(this);
        lm.setFlexDirection(FlexDirection.ROW);
        lm.setFlexWrap(FlexWrap.WRAP);
        lm.setJustifyContent(JustifyContent.FLEX_START);
        rvTiles.setLayoutManager(lm);

        adapter = new TileAdapter(availableTiles, this);
        rvTiles.setAdapter(adapter);

        resetGame();

        btnBack.setOnClickListener(v -> goToMiniGamesMenu());

        btnEvaluate.setOnClickListener(v -> {
            if (selectedTiles.isEmpty()) return;

            String pwd = buildPasswordStringFromSelected();
            PasswordEvaluator.EvaluationResult result = PasswordEvaluator.evaluate(pwd);
            applyStrengthToUi(result);
            showResultDialog(result);

            showResultModeButtons();
        });

        btnRepeat.setOnClickListener(v -> resetGame());

        btnFinish.setOnClickListener(v -> goToMiniGamesMenu());
    }

    private void goToMiniGamesMenu() {
        Intent intent = new Intent(this, MiniGamesMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

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

    private void setupTilesRandom() {
        availableTiles.clear();

        final int TOTAL = 16;
        final int WORD_COUNT = 10;
        final int NUM_COUNT = 3;
        final int SYM_COUNT = 2;
        final int BAD_COUNT = 1;

        HashSet<String> usedWords = new HashSet<>();
        HashSet<String> usedNums = new HashSet<>();
        HashSet<String> usedSyms = new HashSet<>();
        HashSet<String> usedBad = new HashSet<>();

        for (int i = 0; i < WORD_COUNT; i++) {
            String w = pickUnique(WORDS, usedWords);
            availableTiles.add(new TileItem(w, TileItem.Type.WORD, availableTiles.size()));
        }

        for (int i = 0; i < NUM_COUNT; i++) {
            String n = pickUnique(NUMBERS, usedNums);
            availableTiles.add(new TileItem(n, TileItem.Type.NUMBER, availableTiles.size()));
        }

        for (int i = 0; i < SYM_COUNT; i++) {
            String s = pickUnique(SYMBOLS, usedSyms);
            availableTiles.add(new TileItem(s, TileItem.Type.SYMBOL, availableTiles.size()));
        }

        for (int i = 0; i < BAD_COUNT; i++) {
            String b = pickUnique(BAD, usedBad);
            availableTiles.add(new TileItem(b, TileItem.Type.BAD, availableTiles.size()));
        }

        while (availableTiles.size() < TOTAL) {
            String extra = pickUnique(SEPARATORS, usedSyms);
            availableTiles.add(new TileItem(extra, TileItem.Type.SEPARATOR, availableTiles.size()));
        }

        Collections.shuffle(availableTiles, rng);

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

    @Override
    public void onTileClick(TileItem item) {
        if (isResultMode()) return;

        int pos = adapter.indexOf(item);
        if (pos >= 0) adapter.removeAt(pos);

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
            if (isResultMode()) return;
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

    private void recalcAndUpdateUi() {
        boolean hasAny = !selectedTiles.isEmpty();

        btnBack.setEnabled(true);

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

    private void resetGame() {
        showEvaluateModeButtons();

        chipsContainer.removeAllViews();
        selectedTiles.clear();

        setupTilesRandom();
        if (adapter != null) adapter.notifyDataSetChanged();

        progressStrength.setProgress(0);
        tvStrength.setText("Moc hasła: 0%");

        btnBack.setEnabled(true);
        btnEvaluate.setEnabled(false);
    }
}
