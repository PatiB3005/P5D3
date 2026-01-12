package com.example.smartsenior.ui.trustedContacts;

import androidx.annotation.Nullable;

public final class PhoneUtils {
    private PhoneUtils() {}

    public static String digitsOnly(@Nullable String s) {
        if (s == null) return "";
        return s.replaceAll("\\D+", "");
    }

    /**
     * Zwraca +48XXXXXXXXX albo null jeśli niepoprawny.
     * Akceptuje: 9 cyfr, +48..., 48..., 0048...
     */
    @Nullable
    public static String normalizeToPL(@Nullable String input) {
        String d = digitsOnly(input);
        if (d.isEmpty()) return null;

        // 1) Jeśli użytkownik wpisał dokładnie 9 cyfr -> to jest numer krajowy (NIGDY nie zgadujemy prefiksów)
        if (d.length() == 9) {
            return "+48" + d;
        }

        // 2) Jeśli jest 0048 + 9 cyfr (13 cyfr) -> bierz ostatnie 9
        if (d.length() == 13 && d.startsWith("0048")) {
            return "+48" + d.substring(4); // po 0048 zostaje 9 cyfr
        }

        // 3) Jeśli jest 48 + 9 cyfr (11 cyfr) -> bierz ostatnie 9
        if (d.length() == 11 && d.startsWith("48")) {
            return "+48" + d.substring(2); // po 48 zostaje 9 cyfr
        }

        // Inne długości = niepoprawne dla naszego przypadku
        return null;
    }

    /** Wyciąga 9 cyfr krajowych z dowolnego wejścia. */
    public static String national9FromAny(@Nullable String input) {
        String norm = normalizeToPL(input);
        if (norm == null) return "";
        return norm.substring(3);
    }
}
