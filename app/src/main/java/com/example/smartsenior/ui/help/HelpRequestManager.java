package com.example.smartsenior.ui.help;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.example.smartsenior.ui.trustedContacts.TrustedContact;
import com.example.smartsenior.ui.trustedContacts.TrustedContactsActivity;
import com.example.smartsenior.ui.trustedContacts.TrustedContactsStorage;

import java.util.ArrayList;

public class HelpRequestManager {

    private final Context ctx;
    private final ActivityResultLauncher<String> permissionLauncher;

    private ArrayList<TrustedContact> pendingContacts;

    public HelpRequestManager(Context ctx, ActivityResultLauncher<String> permissionLauncher) {
        this.ctx = ctx;
        this.permissionLauncher = permissionLauncher;
    }

    public void startHelpFlow() {
        ArrayList<TrustedContact> contacts = TrustedContactsStorage.load(ctx);

        if (contacts == null || contacts.isEmpty()) {
            new AlertDialog.Builder(ctx)
                    .setTitle("Brak zaufanych kontaktów")
                    .setMessage("Aby wysłać prośbę o wsparcie, dodaj przynajmniej jeden zaufany kontakt.")
                    .setPositiveButton("Dodaj kontakt", (d, w) -> {
                        ctx.startActivity(new Intent(ctx, TrustedContactsActivity.class));
                    })
                    .setNegativeButton("Anuluj", null)
                    .show();
            return;
        }

        new AlertDialog.Builder(ctx)
                .setTitle("Wyślij prośbę o wsparcie?")
                .setMessage("Wyślę SMS do " + contacts.size() + " zaufanych kontaktów.\n\nUwaga: mogą wystąpić koszty operatora.")
                .setPositiveButton("Wyślij", (d, w) -> {
                    pendingContacts = contacts;
                    ensurePermissionAndSend();
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void ensurePermissionAndSend() {
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            sendNow();
        } else {
            permissionLauncher.launch(Manifest.permission.SEND_SMS);
        }
    }

    public void onSmsPermissionResult(boolean granted) {
        if (!granted) {
            new AlertDialog.Builder(ctx)
                    .setTitle("Pomoc")
                    .setMessage("Brak zgody na SMS – nie mogę wysłać prośby o wsparcie.")
                    .setPositiveButton("OK", null)
                    .show();
            pendingContacts = null;
            return;
        }
        sendNow();
    }

    private void sendNow() {
        if (pendingContacts == null || pendingContacts.isEmpty()) return;

        String msg = HelpMessageFactory.build(ctx);
        HelpSmsSender.Result r = HelpSmsSender.sendToAll(ctx, pendingContacts, msg);

        ctx.getSharedPreferences("help_prefs", Context.MODE_PRIVATE)
                .edit()
                .putLong("last_help_sent_ms", System.currentTimeMillis())
                .apply();

        String info = "Prośba o wsparcie wysłana";
        if (r.sentCount > 0) info += " do " + r.sentCount + " kontaktów.";
        if (r.failCount > 0) info += "\nNie udało się wysłać do: " + r.failCount + ".";

        new AlertDialog.Builder(ctx)
                .setTitle("Pomoc")
                .setMessage(info)
                .setPositiveButton("OK", null)
                .show();

        pendingContacts = null;
    }
}
