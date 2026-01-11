package com.example.smartsenior.ui.miniGamesMenu.miniGames;

public class TileItem {

    public enum Type { WORD, NUMBER, SYMBOL, SEPARATOR, BAD }

    public final String text;
    public final Type type;
    public final int originalIndex;

    public TileItem(String text, Type type, int originalIndex) {
        this.text = text;
        this.type = type;
        this.originalIndex = originalIndex;
    }
}
