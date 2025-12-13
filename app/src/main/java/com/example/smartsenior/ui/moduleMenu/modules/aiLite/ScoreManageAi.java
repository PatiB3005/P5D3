package com.example.smartsenior.ui.moduleMenu.modules.aiLite;

public class ScoreManageAi {
    public static int score = 0;

    public static void addPoint() {
        score++;
    }

    public static int getScore() {
        return score;
    }

    public static void reset() {
        score = 0;
    }
}
