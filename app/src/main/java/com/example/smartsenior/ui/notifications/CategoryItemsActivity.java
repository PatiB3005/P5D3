package com.example.smartsenior.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

    private View emptyCard;
    private TextView tvEmptyTitle;
    private TextView tvEmptyDesc;
    private TextView tvEmptySteps;

    private ReminderType type;

    private final SimpleDateFormat fmt =
            new SimpleDateFormat("dd.MM.yyyy  HH:mm", new Locale("pl", "PL"));

    private final ArrayList<ShoppingList> currentShoppingLists = new ArrayList<>();
    private final ArrayList<Reminder> currentReminders = new ArrayList<>();

    private static class RowUi {
        final String title;
        final String subtitle;
        final String hint;

        RowUi(String title, String subtitle, String hint) {
            this.title = title;
            this.subtitle = subtitle;
            this.hint = hint;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        topAppBar.setTitle("");

        tvScreenTitle = findViewById(R.id.tvScreenTitle);
        listView = findViewById(R.id.listItems);

        emptyCard = findViewById(R.id.emptyCard);
        tvEmptyTitle = findViewById(R.id.tvEmptyTitle);
        tvEmptyDesc = findViewById(R.id.tvEmptyDesc);
        tvEmptySteps = findViewById(R.id.tvEmptySteps);

        if (emptyCard != null) {
            listView.setEmptyView(emptyCard);
        }

        String typeName = getIntent().getStringExtra(EXTRA_TYPE);
        type = ReminderType.OTHER;
        if (typeName != null) {
            try { type = ReminderType.valueOf(typeName); } catch (Exception ignored) {}
        }

        tvScreenTitle.setText(type.label);
        setEmptyTexts();

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (type == ReminderType.SHOPPING) {
                if (position < 0 || position >= currentShoppingLists.size()) return true;
                ShoppingList sl = currentShoppingLists.get(position);

                showPrettyConfirm(
                        "Usunąć listę zakupów?",
                        sl.title,
                        "Usuń",
                        () -> {
                            ShoppingListStore.removeById(this, sl.id);
                            refresh();
                        },
                        "Anuluj",
                        null
                );

            } else {
                if (position < 0 || position >= currentReminders.size()) return true;
                Reminder r = currentReminders.get(position);

                showPrettyConfirm(
                        "Usunąć wpis?",
                        r.title,
                        "Usuń",
                        () -> {
                            ReminderScheduler.cancel(this, r.id);
                            ReminderStore.removeById(this, r.id);
                            refresh();
                        },
                        "Anuluj",
                        null
                );
            }
            return true;
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (type == ReminderType.SHOPPING) {
                if (position < 0 || position >= currentShoppingLists.size()) return;

                ShoppingList sl = currentShoppingLists.get(position);
                Intent i = new Intent(this, ShoppingListDetailActivity.class);
                i.putExtra(ShoppingListDetailActivity.EXTRA_LIST_ID, sl.id);
                startActivity(i);
                return;
            }

            if (position < 0 || position >= currentReminders.size()) return;
            Reminder r = currentReminders.get(position);

            String whenStr = fmt.format(new Date(r.timeMillis));
            String rep = "";
            if (r.repeatMinutes > 0 && r.type == ReminderType.MEDS) {
                int h = Math.max(1, r.repeatMinutes / 60);
                rep = "\nPowtarzaj: co " + h + " godz.";
            }

            showPrettyOk("Podgląd", (r.title == null ? "" : r.title) + "\n\n" + whenStr + rep);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void setEmptyTexts() {
        if (type == ReminderType.SHOPPING) {
            if (tvEmptyTitle != null) tvEmptyTitle.setText("Brak list zakupów");
            if (tvEmptyDesc != null) tvEmptyDesc.setText("Dodaj pierwszą listę zakupów na ekranie Przypomnienia.");
            if (tvEmptySteps != null) tvEmptySteps.setText("1) Wróć strzałką\n2) Kliknij „Dodaj przypomnienie”\n3) Wybierz „Lista zakupów”");
        } else if (type == ReminderType.MEDS) {
            if (tvEmptyTitle != null) tvEmptyTitle.setText("Brak leków");
            if (tvEmptyDesc != null) tvEmptyDesc.setText("Dodaj przypomnienie o leku na ekranie Przypomnienia.");
            if (tvEmptySteps != null) tvEmptySteps.setText("1) Wróć strzałką\n2) Kliknij „Dodaj przypomnienie”\n3) Wybierz „Leki”");
        } else if (type == ReminderType.VISIT) {
            if (tvEmptyTitle != null) tvEmptyTitle.setText("Brak wizyt");
            if (tvEmptyDesc != null) tvEmptyDesc.setText("Dodaj przypomnienie o wizycie na ekranie Przypomnienia.");
            if (tvEmptySteps != null) tvEmptySteps.setText("1) Wróć strzałką\n2) Kliknij „Dodaj przypomnienie”\n3) Wybierz „Wizyta u lekarza”");
        } else {
            if (tvEmptyTitle != null) tvEmptyTitle.setText("Brak wpisów");
            if (tvEmptyDesc != null) tvEmptyDesc.setText("Dodaj nowe wydarzenie na ekranie Przypomnienia.");
            if (tvEmptySteps != null) tvEmptySteps.setText("1) Wróć strzałką\n2) Kliknij „Dodaj przypomnienie”\n3) Wybierz „Inne wydarzenie”");
        }
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

    private ArrayAdapter<RowUi> makeAdapter(ArrayList<RowUi> rows) {
        return new ArrayAdapter<RowUi>(this, R.layout.item_reminder_row, R.id.tvRowTitle, rows) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                TextView t1 = v.findViewById(R.id.tvRowTitle);
                TextView t2 = v.findViewById(R.id.tvRowSubtitle);
                TextView t3 = v.findViewById(R.id.tvRowHint);

                RowUi r = getItem(position);
                if (r != null) {
                    if (t1 != null) t1.setText(r.title);
                    if (t2 != null) t2.setText(r.subtitle);
                    if (t3 != null) t3.setText(r.hint);
                }
                return v;
            }
        };
    }

    private void refresh() {
        ArrayList<RowUi> rows = new ArrayList<>();
        currentShoppingLists.clear();
        currentReminders.clear();

        if (type == ReminderType.SHOPPING) {
            ArrayList<ShoppingList> lists = ShoppingListStore.load(this);
            Collections.sort(lists, Comparator.comparingLong(o -> o.createdAtMillis));
            currentShoppingLists.addAll(lists);

            for (ShoppingList sl : lists) {
                int total = sl.items.size();
                int done = 0;
                for (ShoppingItem it : sl.items) if (it.checked) done++;

                String line1 = sl.title + "  (" + done + "/" + total + ")";
                String line2 = fmt.format(new Date(sl.createdAtMillis));
                rows.add(new RowUi(line1, line2, "Naciśnij, aby otworzyć"));
            }
        } else {
            ArrayList<Reminder> list = loadTypeReminders();
            currentReminders.addAll(list);

            for (Reminder r : list) {
                String whenStr = fmt.format(new Date(r.timeMillis));

                String rep = "";
                if (r.repeatMinutes > 0 && r.type == ReminderType.MEDS) {
                    int h = Math.max(1, r.repeatMinutes / 60);
                    rep = " • co " + h + " godz.";
                }

                rows.add(new RowUi(
                        r.title == null ? "" : r.title,
                        whenStr + rep,
                        "Naciśnij, aby zobaczyć"
                ));
            }
        }

        listView.setAdapter(makeAdapter(rows));
    }


    private void showPrettyOk(String title, String message) {
        View v = getLayoutInflater().inflate(R.layout.dialog_pretty_ok, null);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvMessage = v.findViewById(R.id.tvMessage);
        View btnOk = v.findViewById(R.id.btnOk);

        tvTitle.setText(title);
        tvMessage.setText(message);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(true)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnOk.setOnClickListener(x -> dialog.dismiss());
        dialog.show();
    }

    private void showPrettyConfirm(String title, String message,
                                   String positiveText, Runnable onPositive,
                                   String negativeText, Runnable onNegative) {
        View v = getLayoutInflater().inflate(R.layout.dialog_pretty_confirm, null);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvMessage = v.findViewById(R.id.tvMessage);
        TextView btnPos = v.findViewById(R.id.btnPositive);
        TextView btnNeg = v.findViewById(R.id.btnNegative);

        tvTitle.setText(title);
        tvMessage.setText(message);
        btnPos.setText(positiveText);
        btnNeg.setText(negativeText);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(true)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnPos.setOnClickListener(x -> {
            dialog.dismiss();
            if (onPositive != null) onPositive.run();
        });

        btnNeg.setOnClickListener(x -> {
            dialog.dismiss();
            if (onNegative != null) onNegative.run();
        });

        dialog.show();
    }
}
