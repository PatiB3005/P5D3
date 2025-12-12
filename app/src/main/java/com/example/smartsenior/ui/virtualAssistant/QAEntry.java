package com.example.smartsenior.ui.virtualAssistant;

import java.util.List;
import java.util.Set;

public class QAEntry {
    public final String question;
    public final String answer;

    public final String questionNorm;
    public final Set<String> qTokens;
    public final List<String> keywordsNorm;

    public QAEntry(String question,
                   String answer,
                   String questionNorm,
                   Set<String> qTokens,
                   List<String> keywordsNorm) {
        this.question = question;
        this.answer = answer;
        this.questionNorm = questionNorm;
        this.qTokens = qTokens;
        this.keywordsNorm = keywordsNorm;
    }
}
