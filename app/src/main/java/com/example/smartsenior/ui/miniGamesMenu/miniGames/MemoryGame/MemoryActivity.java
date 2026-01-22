package com.example.smartsenior.ui.miniGamesMenu.miniGames.MemoryGame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsenior.R;
import com.example.smartsenior.data.scores.HighScoreStore;
import com.example.smartsenior.data.scores.ScoreKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MemoryActivity extends AppCompatActivity {

    private RecyclerView rvBoard;
    private TextView tvMoves;
    private TextView tvHighScore;

    private MemoryBoardAdapter adapter;
    private MemoryGame game;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean lockClicks = false;

    private long startTimeMs;
    private boolean winHandled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        tvHighScore = findViewById(R.id.tvHighScore);
        tvMoves = findViewById(R.id.tvMoves);
        rvBoard = findViewById(R.id.rvBoard);

        startTimeMs = SystemClock.elapsedRealtime();

        int best = HighScoreStore.getHighScore(this, ScoreKeys.MG1_MEMORY_HIGH_SCORE);
        tvHighScore.setText("Rekord: " + best);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnRestart).setOnClickListener(v -> recreate());

        int columns = 3;
        int totalCards = 12;
        int pairCount = totalCards / 2;

        List<Card> deck = createDeck(pairCount);
        game = new MemoryGame(deck);

        adapter = new MemoryBoardAdapter(game.getCards(), pos -> {
            if (lockClicks || winHandled) return;

            List<Integer> changed = game.flipCard(pos);
            if (changed.isEmpty()) return;

            for (int idx : changed) adapter.notifyItemChanged(idx);
            tvMoves.setText("Ruchy: " + game.getMoves());

            int faceUpUnmatched = 0;
            for (Card c : game.getCards()) {
                if (c.isFaceUp() && !c.isMatched()) faceUpUnmatched++;
            }

            if (faceUpUnmatched == 2) {
                lockClicks = true;
                handler.postDelayed(() -> {
                    List<Integer> backChanged = game.faceDownUnmatchedFaceUp();
                    for (int idx : backChanged) adapter.notifyItemChanged(idx);
                    lockClicks = false;

                    if (game.hasWon()) handleWinOnce();
                }, 800);
            } else {
                if (game.hasWon()) handleWinOnce();
            }
        });

        rvBoard.setLayoutManager(new GridLayoutManager(this, columns));
        rvBoard.setAdapter(adapter);
        rvBoard.setHasFixedSize(true);

        tvMoves.setText("Ruchy: 0");
    }

    private void handleWinOnce() {
        if (winHandled) return;
        winHandled = true;

        int moves = game.getMoves();
        int timeSec = (int) ((SystemClock.elapsedRealtime() - startTimeMs) / 1000);

        int score = calcMemoryScore(moves, timeSec);

        boolean newRecord = HighScoreStore.submitHighScore(this, ScoreKeys.MG1_MEMORY_HIGH_SCORE, score);
        int best = HighScoreStore.getHighScore(this, ScoreKeys.MG1_MEMORY_HIGH_SCORE);

        tvHighScore.setText("Rekord: " + best);

        String msg = "Wygrana! Punkty: " + score
                + " | Ruchy: " + moves
                + " | Czas: " + timeSec + "s"
                + (newRecord ? " (Nowy rekord!)" : "");

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private int calcMemoryScore(int moves, int timeSec) {
        int score = 1000 - (moves * 40) - (timeSec * 2);
        return Math.max(0, score);
    }

    private List<Card> createDeck(int pairCount) {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.fake_news_4);
        images.add(R.drawable.ic_fake_news_5);
        images.add(R.drawable.ic_call_from_unknown6);
        images.add(R.drawable.ic_policeman);
        images.add(R.drawable.ic_safemsg4);
        images.add(R.drawable.ic_safemsg5);
        images.add(R.drawable.ic_safemsg6);
        images.add(R.drawable.ic_suspect_call);
        images.add(R.drawable.ic_verify_store);
        images.add(R.drawable.ic_banker);

        Collections.shuffle(images);

        List<Integer> chosen = images.subList(0, Math.min(pairCount, images.size()));

        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < chosen.size(); i++) {
            int res = chosen.get(i);
            cards.add(new Card(i, res));
            cards.add(new Card(i, res));
        }

        Collections.shuffle(cards, new Random(System.currentTimeMillis()));
        return cards;
    }
}
