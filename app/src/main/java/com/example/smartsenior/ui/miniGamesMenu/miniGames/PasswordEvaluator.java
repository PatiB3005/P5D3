package com.example.smartsenior.ui.miniGamesMenu.miniGames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordEvaluator {

    public enum StrengthLevel { WEAK, MEDIUM, GOOD }

    public static class EvaluationResult {
        public final String password;
        public final int score; // 0..10 (pokazujesz jako %)
        public final StrengthLevel level;
        public final List<String> reasons;
        public final String suggestion;

        public EvaluationResult(String password, int score, StrengthLevel level, List<String> reasons, String suggestion) {
            this.password = password;
            this.score = score;
            this.level = level;
            this.reasons = reasons;
            this.suggestion = suggestion;
        }
    }

    private static final List<String> BAD_FRAGMENTS = Arrays.asList(
            "123", "1234", "1111", "0000", "qwerty", "asdf", "abcd", "password", "admin", "haslo", "hasło"
    );

    private static final Pattern YEAR_PATTERN = Pattern.compile("\\b(19\\d{2}|20\\d{2})\\b");
    private static final Pattern WORD_PLUS_4_DIGITS = Pattern.compile("^[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż]+\\d{4}$");

    // Twardy warunek "silne"
    private static boolean isStrongRule(int len, boolean hasUpper, boolean hasSymbol) {
        return len >= 12 && hasUpper && hasSymbol;
    }

    public static EvaluationResult evaluate(String password) {
        if (password == null) password = "";

        int len = password.length();

        boolean hasLower = false, hasUpper = false, hasDigit = false, hasSymbol = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLetter(c) && Character.isLowerCase(c)) hasLower = true;
            else if (Character.isLetter(c) && Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSymbol = true;
        }

        int score = 0;
        List<String> reasons = new ArrayList<>();

        // --- Punkty za długość (bardziej premiujemy 12+) ---
        if (len >= 16) score += 4;
        else if (len >= 12) score += 3;
        else if (len >= 8) score += 1;

        // --- Punkty za kategorie ---
        if (hasLower) score += 1;
        if (hasUpper) score += 2;   // ważne wg Twojej reguły
        if (hasDigit) score += 1;
        if (hasSymbol) score += 2;  // ważne wg Twojej reguły

        // --- Kary ---
        String lower = password.toLowerCase();
        for (String bad : BAD_FRAGMENTS) {
            if (lower.contains(bad)) {
                score -= 3;
                reasons.add("Zawiera popularny wzór: \"" + bad + "\".");
                break;
            }
        }

        if (YEAR_PATTERN.matcher(password).find()) {
            score -= 2;
            reasons.add("Wygląda jak rok — to łatwe do odgadnięcia.");
        }

        if (WORD_PLUS_4_DIGITS.matcher(password).matches()) {
            score -= 2;
            reasons.add("To wygląda jak \"słowo + 4 cyfry\" (częsty schemat).");
        }

        // clamp 0..10
        if (score < 0) score = 0;
        if (score > 10) score = 10;

        // --- Twarda kwalifikacja poziomu ---
        StrengthLevel level;
        if (isStrongRule(len, hasUpper, hasSymbol)) {
            level = StrengthLevel.GOOD; // silne dopiero gdy spełni 12 + symbol + duża litera
        } else if (score >= 4) {
            level = StrengthLevel.MEDIUM;
        } else {
            level = StrengthLevel.WEAK;
        }

        // --- Powody (krótkie i czytelne) ---
        List<String> baseReasons = new ArrayList<>();
        if (len < 12) baseReasons.add("Za krótkie: celuj w min. 12 znaków.");
        else baseReasons.add("Dobra długość (12+).");

        if (!hasUpper) baseReasons.add("Brakuje wielkiej litery.");
        else baseReasons.add("Jest wielka litera.");

        if (!hasSymbol) baseReasons.add("Brakuje znaku specjalnego (np. !, _, @).");
        else baseReasons.add("Jest znak specjalny.");

        // dokładamy ewentualne kary (jeśli są)
        baseReasons.addAll(reasons);

        // max 3 powody
        List<String> finalReasons = new ArrayList<>();
        for (String r : baseReasons) {
            if (!finalReasons.contains(r)) finalReasons.add(r);
            if (finalReasons.size() == 3) break;
        }

        // --- Sugestia: jedna konkretna ---
        String suggestion = null;
        if (len < 12) suggestion = "Dodaj więcej kafelków, aby mieć min. 12 znaków.";
        else if (!hasSymbol) suggestion = "Dodaj znak specjalny, np. ! albo _.";
        else if (!hasUpper) suggestion = "Dodaj wielką literę (np. zmień jedno słowo na zaczynające się z dużej).";
        else if (YEAR_PATTERN.matcher(password).find()) suggestion = "Zamień rok na inną liczbę (np. 7 lub 58).";

        return new EvaluationResult(password, score, level, finalReasons, suggestion);
    }
}
