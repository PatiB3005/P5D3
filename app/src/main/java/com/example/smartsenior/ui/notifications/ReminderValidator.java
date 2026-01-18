package com.example.smartsenior.ui.notifications;

import java.util.Calendar;

public class ReminderValidator {

    private ReminderValidator() {}

    public static String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) return "Wpisz nazwę.";
        return null;
    }

    public static String validatePickedMillis(Long pickedMillis) {
        if (pickedMillis == null) return "Wybierz datę i godzinę.";
        return null;
    }

    public static String validateFutureTime(Long pickedMillis) {
        if (pickedMillis == null) return "Wybierz datę i godzinę.";

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        long nowRoundedToMinute = c.getTimeInMillis();

        // musi być co najmniej 1 minuta od "pełnej minuty"
        if (pickedMillis < nowRoundedToMinute + 60_000L) {
            return "Wybierz przyszłą datę i godzinę.";
        }
        return null;
    }

    /** repeatHoursStr: string z EditText, może być pusty -> traktujemy jako 0 */
    public static RepeatResult validateRepeatHours(String repeatHoursStr) {
        String s = (repeatHoursStr == null) ? "" : repeatHoursStr.trim();
        if (s.isEmpty()) return new RepeatResult(0, null);

        // tylko cyfry
        if (!s.matches("^\\d+$")) return new RepeatResult(0, "Powtarzanie: wpisz tylko cyfry (0 lub więcej).");

        try {
            int hours = Integer.parseInt(s);
            if (hours < 0) return new RepeatResult(0, "Powtarzanie nie może być ujemne.");
            return new RepeatResult(hours, null);
        } catch (Exception e) {
            return new RepeatResult(0, "Nieprawidłowa wartość powtarzania.");
        }
    }

    public static class RepeatResult {
        public final int hours;
        public final String error;

        public RepeatResult(int hours, String error) {
            this.hours = hours;
            this.error = error;
        }
    }
}
