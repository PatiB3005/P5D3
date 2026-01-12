package com.example.smartsenior.ui.notifications;

public enum ReminderType {
    MEDS("Leki"),
    VISIT("Wizyty u lekarza"),
    SHOPPING("Lista zakupów"),
    OTHER("Inne wydarzenia");

    public final String label;

    ReminderType(String label) { this.label = label; }

    public static ReminderType fromLabel(String label) {
        for (ReminderType t : values()) if (t.label.equals(label)) return t;
        return MEDS;
    }
}

