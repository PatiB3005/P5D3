package com.example.smartsenior.ui.moduleMenu.modules.shopping;

public class ScoreManager {
    public static int score = 0;

    public static void reset() {
        score = 0;
    }

    public static void addPoint() {
        score++;
    }
}
