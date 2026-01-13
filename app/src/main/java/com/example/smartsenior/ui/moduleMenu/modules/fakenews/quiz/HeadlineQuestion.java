package com.example.smartsenior.ui.moduleMenu.modules.fakenews.quiz;

public class HeadlineQuestion {

    private final String headline;
    private final boolean isTrue;
    private final String explanation;

    public HeadlineQuestion(String headline, boolean isTrue, String explanation) {
        this.headline = headline;
        this.isTrue = isTrue;
        this.explanation = explanation;
    }

    public String getHeadline() {
        return headline;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public String getExplanation() {
        return explanation;
    }
}
