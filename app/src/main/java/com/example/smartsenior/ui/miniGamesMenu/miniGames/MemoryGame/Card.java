package com.example.smartsenior.ui.miniGamesMenu.miniGames.MemoryGame;

public class Card {
    private final int pairId;
    private final int imageResId;

    private boolean faceUp = false;
    private boolean matched = false;

    public Card(int pairId, int imageResId) {
        this.pairId = pairId;
        this.imageResId = imageResId;
    }

    public int getPairId() {
        return pairId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}
