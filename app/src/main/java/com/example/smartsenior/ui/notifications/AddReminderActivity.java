package com.example.smartsenior.ui.notifications;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddReminderActivity extends AppCompatActivity {

    private TextView tvTypeChosen;

    private LinearLayout sectionTitle;
    private EditText etTitle;

    private LinearLayout sectionDate;
    private MaterialButton btnPick;
    private TextView tvPicked;

    private LinearLayout sectionRepeat;
    private EditText etRepeat; // GODZINY

    private LinearLayout sectionShopping;
    private EditText etShoppingItem;
    private MaterialButton btnAddItem;
    private ListView listShopping;

    private ReminderType chosenType = null;

    private final Calendar cal = Calendar.getInstance();
    private final SimpleDateFormat fmt =
            new SimpleDateFormat("dd.MM.yyyy  HH:mm", new Locale("pl", "PL"));
    private Long pickedMillis = null;

    private final ArrayList<String> shoppingItems = new ArrayList<>();
    private ArrayAdapter<String> shoppingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        topAppBar.setTitle("");

        tvTypeChosen = findViewById(R.id.tvTypeChosen);

        sectionTitle = findViewById(R.id.sectionTitle);
        sectionDate = findViewById(R.id.sectionDate);
        sectionRepeat = findViewById(R.id.sectionRepeat);

        etTitle = findViewById(R.id.etTitle);
        btnPick = findViewById(R.id.btnPickDateTime);
        tvPicked = findViewById(R.id.tvPicked);
        etRepeat = findViewById(R.id.etRepeatMinutes);

        sectionShopping = findViewById(R.id.sectionShopping);
        etShoppingItem = findViewById(R.id.etShoppingItem);
        btnAddItem = findViewById(R.id.btnAddItem);
        listShopping = findViewById(R.id.listShopping);

        // większa czcionka dla produktów (musisz mieć item_shopping_row.xml)
        shoppingAdapter = new ArrayAdapter<>(this, R.layout.item_shopping_row, R.id.tvItem, shoppingItems);
        listShopping.setAdapter(shoppingAdapter);

        tvTypeChosen.setOnClickListener(v -> showTypePicker());

        btnPick.setOnClickListener(v -> {
            if (chosenType == null) { showError("Najpierw wybierz kategorię."); return; }
            pickDateThenTime();
        });

        btnAddItem.setOnClickListener(v -> {
            String it = etShoppingItem.getText() == null ? "" : etShoppingItem.getText().toString().trim();
            if (it.isEmpty()) return;
            shoppingItems.add(it);
            shoppingAdapter.notifyDataSetChanged();
            etShoppingItem.setText("");
        });

        listShopping.setOnItemLongClickListener((parent, view, position, id) -> {
            shoppingItems.remove(position);
            shoppingAdapter.notifyDataSetChanged();
            return true;
        });

        applyTypeToUi();
        tvPicked.setText("Nie wybrano");
    }

    private void showTypePicker() {
        final String[] labels = new String[] {
                "Leki",
                "Wizyta u lekarza",
                "Lista zakupów",
                "Inne wydarzenie"
        };

        new AlertDialog.Builder(this)
                .setTitle("Wybierz kategorię")
                .setItems(labels, (d, which) -> {
                    if (which == 0) chosenType = ReminderType.MEDS;
                    if (which == 1) chosenType = ReminderType.VISIT;
                    if (which == 2) chosenType = ReminderType.SHOPPING;
                    if (which == 3) chosenType = ReminderType.OTHER;

                    pickedMillis = null;
                    tvPicked.setText("Nie wybrano");
                    etRepeat.setText("");
                    shoppingItems.clear();
                    shoppingAdapter.notifyDataSetChanged();

                    applyTypeToUi();
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void applyTypeToUi() {
        if (chosenType == null) {
            tvTypeChosen.setText("Wybierz kategorię");
            sectionTitle.setVisibility(View.GONE);
            sectionDate.setVisibility(View.GONE);
            sectionRepeat.setVisibility(View.GONE);
            sectionShopping.setVisibility(View.GONE);
            return;
        }

        if (chosenType == ReminderType.MEDS) tvTypeChosen.setText("Leki");
        else if (chosenType == ReminderType.VISIT) tvTypeChosen.setText("Wizyta u lekarza");
        else if (chosenType == ReminderType.SHOPPING) tvTypeChosen.setText("Lista zakupów");
        else tvTypeChosen.setText("Inne wydarzenie");

        if (chosenType == ReminderType.SHOPPING) {
            sectionTitle.setVisibility(View.GONE);
            sectionDate.setVisibility(View.GONE);
            sectionRepeat.setVisibility(View.GONE);
            sectionShopping.setVisibility(View.VISIBLE);
            return;
        }

        sectionShopping.setVisibility(View.GONE);
        sectionTitle.setVisibility(View.VISIBLE);
        sectionDate.setVisibility(View.VISIBLE);

        sectionRepeat.setVisibility(chosenType == ReminderType.MEDS ? View.VISIBLE : View.GONE);
    }

    public void onSaveClicked(View v) {
        if (chosenType == null) { showError("Wybierz kategorię."); return; }

        // ✅ ZAKUPY: zapis do ShoppingListStore (żeby działała zakładka + checklist)
        if (chosenType == ReminderType.SHOPPING) {
            if (shoppingItems.isEmpty()) { showError("Dodaj przynajmniej 1 produkt."); return; }

            int id = ShoppingListStore.nextId(this);
            ShoppingList sl = new ShoppingList(id, "Lista zakupów", System.currentTimeMillis());
            for (String s : shoppingItems) sl.items.add(new ShoppingItem(s, false));

            ShoppingListStore.add(this, sl);
            finish();
            return;
        }

        String title = etTitle.getText() == null ? "" : etTitle.getText().toString().trim();

        String err;
        err = ReminderValidator.validateTitle(title);
        if (err != null) { showError(err); return; }

        err = ReminderValidator.validatePickedMillis(pickedMillis);
        if (err != null) { showError(err); return; }

        err = ReminderValidator.validateFutureTime(pickedMillis);
        if (err != null) { showError(err); return; }

        int repeatMinutes = 0;

        // ✅ repeat tylko dla leków, cyfry 0..∞, 0=brak
        if (sectionRepeat.getVisibility() == View.VISIBLE) {
            ReminderValidator.RepeatResult rr =
                    ReminderValidator.validateRepeatHours(etRepeat.getText() == null ? "" : etRepeat.getText().toString());
            if (rr.error != null) { showError(rr.error); return; }
            repeatMinutes = rr.hours * 60;
        }

        int id = ReminderStore.nextId(this);
        Reminder reminder = new Reminder(id, chosenType, title, pickedMillis, repeatMinutes, "");
        ReminderStore.add(this, reminder);
        ReminderScheduler.schedule(this, reminder);

        finish();
    }

    private void pickDateThenTime() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dp = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog tp = new TimePickerDialog(
                            this,
                            (tv, hour, minute) -> {
                                cal.set(Calendar.HOUR_OF_DAY, hour);
                                cal.set(Calendar.MINUTE, minute);
                                cal.set(Calendar.SECOND, 0);
                                cal.set(Calendar.MILLISECOND, 0);

                                pickedMillis = cal.getTimeInMillis();
                                tvPicked.setText(fmt.format(cal.getTime()));
                            },
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            true
                    );
                    tp.show();
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dp.show();
    }

    private void showError(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Uwaga")
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}
