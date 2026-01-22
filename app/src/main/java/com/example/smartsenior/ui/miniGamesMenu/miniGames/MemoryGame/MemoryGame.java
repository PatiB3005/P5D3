package com.example.smartsenior.ui.miniGamesMenu.miniGames.MemoryGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemoryGame {

    private final List<Card> cards;
    private int moves = 0;
    private Integer indexOfSingleSelectedCard = null;

    public MemoryGame(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getMoves() {
        return moves;
    }

    /** Zwraca listę indeksów kart do odświeżenia (notifyItemChanged). */
    public List<Integer> flipCard(int position) {
        Card card = cards.get(position);
        if (card.isMatched() || card.isFaceUp()) return new ArrayList<>();

        List<Integer> changed = new ArrayList<>();
        Integer singleIndex = indexOfSingleSelectedCard;

        if (singleIndex == null) {
            for (int i = 0; i < cards.size(); i++) {
                Card c = cards.get(i);
                if (!c.isMatched() && c.isFaceUp()) {
                    c.setFaceUp(false);
                    changed.add(i);
                }
            }

            card.setFaceUp(true);
            changed.add(position);
            indexOfSingleSelectedCard = position;

        } else {
            card.setFaceUp(true);
            changed.add(position);

            moves += 1;

            Card other = cards.get(singleIndex);
            if (other.getPairId() == card.getPairId()) {
                other.setMatched(true);
                card.setMatched(true);
                changed.add(singleIndex);
                indexOfSingleSelectedCard = null;
            } else {
                indexOfSingleSelectedCard = null;
            }
        }

        // usuń duplikaty
        Set<Integer> set = new HashSet<>(changed);
        return new ArrayList<>(set);
    }

    public List<Integer> faceDownUnmatchedFaceUp() {
        List<Integer> changed = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            if (!c.isMatched() && c.isFaceUp()) {
                c.setFaceUp(false);
                changed.add(i);
            }
        }
        return changed;
    }

    public boolean hasWon() {
        for (Card c : cards) {
            if (!c.isMatched()) return false;
        }
        return true;
    }
}
