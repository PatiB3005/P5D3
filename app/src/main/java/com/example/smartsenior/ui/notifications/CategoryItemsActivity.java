package com.example.smartsenior.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class CategoryItemsActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "extra_type";

    private ListView listView;
    private TextView tvScreenTitle;
    private ReminderType type;

    private final SimpleDateFormat fmt =
            new SimpleDateFormat("dd.MM.yyyy  HH:mm", new Locale("pl", "PL"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());

        // ✅ toolbar ma być bez tytułu – tylko strzałka
        topAppBar.setTitle("");

        tvScreenTitle = findViewById(R.id.tvScreenTitle);
        listView = findViewById(R.id.listItems);

        String typeName = getIntent().getStringExtra(EXTRA_TYPE);
        type = ReminderType.OTHER;
        if (typeName != null) {
            try { type = ReminderType.valueOf(typeName); } catch (Exception ignored) {}
        }

        // ✅ duży tytuł na ekranie
        tvScreenTitle.setText(type.label);

        // Long click: usuń
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (type == ReminderType.SHOPPING) {
                ArrayList<ShoppingList> lists = ShoppingListStore.load(this);
                Collections.sort(lists, Comparator.comparingLong(o -> o.createdAtMillis));
                if (position < 0 || position >= lists.size()) return true;
                ShoppingList sl = lists.get(position);

                new AlertDialog.Builder(this)
                        .setTitle("Usunąć listę zakupów?")
                        .setMessage(sl.title)
                        .setPositiveButton("Usuń", (d, w) -> {
                            ShoppingListStore.removeById(this, sl.id);
                            refresh();
                        })
                        .setNegativeButton("Anuluj", null)
                        .show();
            } else {
                ArrayList<Reminder> list = loadTypeReminders();
                if (position < 0 || position >= list.size()) return true;
                Reminder r = list.get(position);

                new AlertDialog.Builder(this)
                        .setTitle("Usunąć wpis?")
                        .setMessage(r.title)
                        .setPositiveButton("Usuń", (d, w) -> {
                            ReminderScheduler.cancel(this, r.id);
                            ReminderStore.removeById(this, r.id);
                            refresh();
                        })
                        .setNegativeButton("Anuluj", null)
                        .show();
            }
            return true;
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (type != ReminderType.SHOPPING) return;

            ArrayList<ShoppingList> lists = ShoppingListStore.load(this);
            Collections.sort(lists, Comparator.comparingLong(o -> o.createdAtMillis));
            if (position < 0 || position >= lists.size()) return;

            ShoppingList sl = lists.get(position);

            Intent i = new Intent(this, ShoppingListDetailActivity.class);
            i.putExtra(ShoppingListDetailActivity.EXTRA_LIST_ID, sl.id);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private ArrayList<Reminder> loadTypeReminders() {
        ArrayList<Reminder> all = ReminderStore.load(this);
        ArrayList<Reminder> out = new ArrayList<>();
        for (Reminder r : all) {
            if (r.type == type) out.add(r);
        }
        Collections.sort(out, Comparator.comparingLong(o -> o.timeMillis));
        return out;
    }

    private void refresh() {
        ArrayList<String> rows = new ArrayList<>();

        if (type == ReminderType.SHOPPING) {
            ArrayList<ShoppingList> lists = ShoppingListStore.load(this);
            Collections.sort(lists, Comparator.comparingLong(o -> o.createdAtMillis));

            for (ShoppingList sl : lists) {
                int total = sl.items.size();
                int done = 0;
                for (ShoppingItem it : sl.items) if (it.checked) done++;

                String line1 = sl.title + "  (" + done + "/" + total + ")";
                String line2 = fmt.format(new Date(sl.createdAtMillis));
                rows.add(line1 + "\n" + line2);
            }
        } else {
            ArrayList<Reminder> list = loadTypeReminders();
            for (Reminder r : list) {
                String whenStr = fmt.format(new Date(r.timeMillis));

                // ✅ powtarzanie pokazujemy w godzinach (a nie minutach)
                String rep = "";
                if (r.repeatMinutes > 0 && r.type == ReminderType.MEDS) {
                    int h = Math.max(1, r.repeatMinutes / 60);
                    rep = " • co " + h + " godz.";
                }

                rows.add(r.title + "\n" + whenStr + rep);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_reminder_row,
                R.id.tvRowTitle,
                rows
        );
        listView.setAdapter(adapter);
    }
}
