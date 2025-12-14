package com.example.smartsenior.ui.help;

import android.content.Context;
import android.os.Build;
import android.telephony.SmsManager;

import com.example.smartsenior.ui.trustedContacts.TrustedContact;

import java.util.ArrayList;

public class HelpSmsSender {

    public static class Result {
        public int sentCount;
        public int failCount;
    }

    public static Result sendToAll(Context ctx, ArrayList<TrustedContact> contacts, String message) {
        Result r = new Result();
        if (contacts == null || contacts.isEmpty()) return r;

        SmsManager smsManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            smsManager = ctx.getSystemService(SmsManager.class);
        } else {
            smsManager = SmsManager.getDefault();
        }

        for (TrustedContact c : contacts) {
            String phone = normalizePhone(c == null ? null : c.phone);
            if (phone.isEmpty()) {
                r.failCount++;
                continue;
            }

            try {
                ArrayList<String> parts = smsManager.divideMessage(message);
                smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
                r.sentCount++;
            } catch (Exception e) {
                r.failCount++;
            }
        }
        return r;
    }

    private static String normalizePhone(String phone) {
        if (phone == null) return "";
        String cleaned = phone.replaceAll("[^0-9+]", "");

        if (!cleaned.startsWith("+") && cleaned.length() == 9) {
            cleaned = "+48" + cleaned;
        }
        return cleaned;
    }
}
