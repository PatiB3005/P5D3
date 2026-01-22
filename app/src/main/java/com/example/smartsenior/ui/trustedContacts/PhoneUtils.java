package com.example.smartsenior.ui.trustedContacts;

import androidx.annotation.Nullable;

public final class PhoneUtils {
    private PhoneUtils() {}

    public static String digitsOnly(@Nullable String s) {
        if (s == null) return "";
        return s.replaceAll("\\D+", "");
    }


    @Nullable
    public static String normalizeToPL(@Nullable String input) {
        String d = digitsOnly(input);
        if (d.isEmpty()) return null;

        if (d.length() == 9) {
            return "+48" + d;
        }

        if (d.length() == 13 && d.startsWith("0048")) {
            return "+48" + d.substring(4);
        }

        if (d.length() == 11 && d.startsWith("48")) {
            return "+48" + d.substring(2);
        }

        return null;
    }

    public static String national9FromAny(@Nullable String input) {
        String norm = normalizeToPL(input);
        if (norm == null) return "";
        return norm.substring(3);
    }
}
