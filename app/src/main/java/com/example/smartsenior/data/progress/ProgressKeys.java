package com.example.smartsenior.data.progress;

public final class ProgressKeys {

    // ===== MODUŁ 1: Bezpieczne wiadomości =====
    public static final String M1_THEORY_DONE    = "m1_theory_done";
    public static final String M1_SMS_DONE       = "m1_sms_done";
    public static final String M1_TASK1_DONE     = "m1_task1_done";
    public static final String M1_TASK2_DONE     = "m1_task2_done";
    public static final String M1_TASK3_DONE     = "m1_task3_done";
    public static final String M1_TASK4_DONE     = "m1_task4_done";

    public static final String[] MODULE1_PARTS = {
            M1_THEORY_DONE, M1_SMS_DONE, M1_TASK1_DONE, M1_TASK2_DONE, M1_TASK3_DONE, M1_TASK4_DONE
    };

    // ===== MODUŁ 2: Telefon od podejrzanego nieznajomego =====
    public static final String M2_THEORY_DONE    = "m2_theory_done";
    public static final String M2_GRANDCHILD_DONE= "m2_grandchild_done";
    public static final String M2_POLICEMAN_DONE = "m2_policeman_done";
    public static final String M2_BANK_DONE      = "m2_bank_done";

    public static final String[] MODULE2_PARTS = {
            M2_THEORY_DONE, M2_GRANDCHILD_DONE, M2_POLICEMAN_DONE, M2_BANK_DONE
    };

    // ===== MODUŁ 3: Zakupy Online =====
    public static final String M3_THEORY_DONE    = "m3_theory_done";
    public static final String M3_WEBSITE_DONE   = "m3_website_done";
    public static final String M3_QUIZ_DONE      = "m3_quiz_done";

    public static final String[] MODULE3_PARTS = {
            M3_THEORY_DONE, M3_WEBSITE_DONE, M3_QUIZ_DONE
    };

    // ===== FAKE NEWS =====
    public static final String FN_QUIZ_DONE      = "fn_quiz_done";
    public static final String[] FAKENEWS_PARTS  = { FN_QUIZ_DONE };

    private ProgressKeys() {}
}
