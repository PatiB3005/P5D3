package com.example.smartsenior.ui.notifications;

public class Reminder {
    public int id;
    public ReminderType type;
    public String title;
    public long timeMillis;
    public int repeatMinutes;

    // NOWE: dodatkowe dane (np. lista zakupów)
    public String extra;

    public Reminder(int id, ReminderType type, String title, long timeMillis, int repeatMinutes, String extra) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.timeMillis = timeMillis;
        this.repeatMinutes = repeatMinutes;
        this.extra = extra == null ? "" : extra;
    }
}
