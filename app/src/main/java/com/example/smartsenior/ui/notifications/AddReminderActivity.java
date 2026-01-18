package com.example.smartsenior.ui.notifications;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

    // ✅ Kategorie dla listy zakupów (widoczne tylko w trybie grupowania)
    private TextInputLayout tilCategory;
    private MaterialAutoCompleteTextView acCategory;

    private ReminderType chosenType = null;

    private final Calendar cal = Calendar.getInstance();
    private final SimpleDateFormat fmt =
            new SimpleDateFormat("dd.MM.yyyy  HH:mm", new Locale("pl", "PL"));
    private Long pickedMillis = null;

    private final ArrayList<String> shoppingItems = new ArrayList<>();
    private ArrayAdapter<String> shoppingAdapter;

    // ✅ tryb grupowania
    private boolean groupByCategory = false;

    // separator w tekście (czytelny)
    private static final String SEP = " • ";

    private String categoryOfRow(String row) {
        if (row == null) return "";
        String t = row.trim();
        int i = t.indexOf(SEP);
        return i >= 0 ? t.substring(0, i).trim() : "";
    }

    private String itemOfRow(String row) {
        if (row == null) return "";
        String t = row.trim();
        int i = t.indexOf(SEP);
        return i >= 0 ? t.substring(i + SEP.length()).trim() : t.trim();
    }

    // ✅ duplikaty liczymy po NAZWIE produktu (bez kategorii)
    private String baseName(String s) {
        return itemOfRow(s);
    }

    private int findExistingBaseIndex(String name) {
        String base = baseName(name);
        for (int i = 0; i < shoppingItems.size(); i++) {
            if (baseName(shoppingItems.get(i)).equalsIgnoreCase(base)) return i;
        }
        return -1;
    }

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

        // ✅ nowe widoki do kategorii
        tilCategory = findViewById(R.id.tilCategory);
        acCategory = findViewById(R.id.acCategory);

        // Dropdown kategorii
        String[] categories = new String[]{
                "Warzywa i owoce",
                "Nabiał",
                "Pieczywo",
                "Mięso i ryby",
                "Napoje",
                "Chemia",
                "Inne"
        };

        if (acCategory != null) {
            ArrayAdapter<String> catAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    categories
            );
            acCategory.setAdapter(catAdapter);
            acCategory.setText(categories[0], false);
        }

        // większa czcionka dla produktów (item_shopping_row.xml)
        shoppingAdapter = new ArrayAdapter<>(this, R.layout.item_shopping_row, R.id.tvItem, shoppingItems);
        listShopping.setAdapter(shoppingAdapter);
        updateShoppingListHeight(); // ✅ pokaż całość od razu

        tvTypeChosen.setOnClickListener(v -> showTypePicker());

        btnPick.setOnClickListener(v -> {
            if (chosenType == null) {
                showError("Najpierw wybierz kategorię.");
                return;
            }
            pickDateThenTime();
        });

        // ✅ Dodawanie produktu: blokada duplikatów + (opcjonalnie) kategoria
        btnAddItem.setOnClickListener(v -> {
            String it = etShoppingItem.getText() == null ? "" : etShoppingItem.getText().toString().trim();
            if (it.isEmpty()) {
                showError("Wpisz nazwę produktu.");
                return;
            }

            if (groupByCategory) {
                String cat = (acCategory.getText() == null) ? "" : acCategory.getText().toString().trim();
                if (cat.isEmpty()) {
                    showError("Wybierz kategorię produktu.");
                    return;
                }
            }

            int idx = findExistingBaseIndex(it);
            if (idx >= 0) {
                showError("Już dodałeś ten produkt.");
                etShoppingItem.setText("");
                return;
            }

            if (groupByCategory) {
                String cat = (acCategory.getText() == null) ? "" : acCategory.getText().toString().trim();
                shoppingItems.add(cat + SEP + it);
                sortShoppingItems();
            } else {
                shoppingItems.add(it);
            }

            shoppingAdapter.notifyDataSetChanged();
            updateShoppingListHeight();
            etShoppingItem.setText("");
        });

        // ✅ Usuwanie: przytrzymaj produkt (bez dialogu - jak chcesz, też mogę tu dodać potwierdzenie)
        listShopping.setOnItemLongClickListener((parent, view, position, id) -> {
            shoppingItems.remove(position);
            shoppingAdapter.notifyDataSetChanged();
            updateShoppingListHeight();
            return true;
        });

        applyTypeToUi();
        tvPicked.setText("Nie wybrano");
    }

    private void sortShoppingItems() {
        Collections.sort(shoppingItems, (a, b) -> {
            String ca = categoryOfRow(a);
            String cb = categoryOfRow(b);

            String na = itemOfRow(a);
            String nb = itemOfRow(b);

            int c = ca.compareToIgnoreCase(cb);
            if (c != 0) return c;
            return na.compareToIgnoreCase(nb);
        });
    }

    // ✅ PODMIENIONE: zamiast systemowego AlertDialog -> nasz dialog z listą
    private void showTypePicker() {
        final String[] labels = new String[]{
                "Leki",
                "Wizyta u lekarza",
                "Lista zakupów",
                "Inne wydarzenie"
        };

        showPrettyList("Wybierz kategorię", labels, which -> {
            if (which == 0) chosenType = ReminderType.MEDS;
            if (which == 1) chosenType = ReminderType.VISIT;
            if (which == 2) chosenType = ReminderType.SHOPPING;
            if (which == 3) chosenType = ReminderType.OTHER;

            pickedMillis = null;
            tvPicked.setText("Nie wybrano");
            etRepeat.setText("");

            shoppingItems.clear();
            shoppingAdapter.notifyDataSetChanged();
            updateShoppingListHeight();

            // ✅ reset trybu grupowania przy każdym wejściu
            groupByCategory = false;
            if (tilCategory != null) tilCategory.setVisibility(View.GONE);

            applyTypeToUi();

            // ✅ jeśli zakupowa → pytamy o grupowanie
            if (chosenType == ReminderType.SHOPPING) {
                askShoppingGroupingMode();
            }
        });
    }

    // ✅ PODMIENIONE: zamiast systemowego AlertDialog -> nasze 2-przyciskowe confirm
    private void askShoppingGroupingMode() {
        showPrettyConfirm(
                "Lista zakupów",
                "Czy chcesz pogrupować produkty według kategorii?",
                "Tak, grupuj",
                () -> {
                    groupByCategory = true;
                    if (tilCategory != null) tilCategory.setVisibility(View.VISIBLE);
                },
                "Nie, zwykła lista",
                () -> {
                    groupByCategory = false;
                    if (tilCategory != null) tilCategory.setVisibility(View.GONE);
                }
        );
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
        if (chosenType == null) {
            showError("Wybierz kategorię.");
            return;
        }

        // ✅ ZAKUPY: zapis do ShoppingListStore
        if (chosenType == ReminderType.SHOPPING) {
            if (shoppingItems.isEmpty()) {
                showError("Dodaj przynajmniej 1 produkt.");
                return;
            }

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

        // ✅ repeat tylko dla leków
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

                    MaterialTimePicker picker = new MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_24H)
                            .setHour(now.get(Calendar.HOUR_OF_DAY))
                            .setMinute(now.get(Calendar.MINUTE))
                            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                            .setTitleText("Ustaw godzinę")
                            .setPositiveButtonText("OK")
                            .setNegativeButtonText("Anuluj")
                            .build();

                    picker.addOnPositiveButtonClickListener(v -> {
                        cal.set(Calendar.HOUR_OF_DAY, picker.getHour());
                        cal.set(Calendar.MINUTE, picker.getMinute());
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);

                        pickedMillis = cal.getTimeInMillis();
                        tvPicked.setText(fmt.format(cal.getTime()));
                    });

                    picker.show(getSupportFragmentManager(), "time_picker");
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dp.show();
    }

    // ✅ Naprawa: ListView w ScrollView -> pokaż wszystkie elementy przed zapisem
    private void updateShoppingListHeight() {
        if (listShopping == null || shoppingAdapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < shoppingAdapter.getCount(); i++) {
            View listItem = shoppingAdapter.getView(i, null, listShopping);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listShopping.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.UNSPECIFIED
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        int dividers = listShopping.getDividerHeight() * Math.max(shoppingAdapter.getCount() - 1, 0);

        ViewGroup.LayoutParams params = listShopping.getLayoutParams();
        params.height = totalHeight + dividers + listShopping.getPaddingTop() + listShopping.getPaddingBottom();
        listShopping.setLayoutParams(params);
        listShopping.requestLayout();
    }

    // ✅ PODMIENIONE: teraz zawsze nasz ładny dialog
    private void showError(String msg) {
        showPrettyOk("Uwaga", msg);
    }

    // ====== PRETTY DIALOGS (bez styles.xml) ======

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

    private interface OnChoice { void onPick(int which); }

    private void showPrettyList(String title, String[] items, OnChoice cb) {
        View v = getLayoutInflater().inflate(R.layout.dialog_pretty_list, null);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        ListView list = v.findViewById(R.id.list);
        View btnCancel = v.findViewById(R.id.btnCancel);

        tvTitle.setText(title);

        ArrayAdapter<String> ad = new ArrayAdapter<>(
                this, R.layout.item_dialog_choice, R.id.tvChoice, items
        );
        list.setAdapter(ad);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(true)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        list.setOnItemClickListener((p, vv, pos, id) -> {
            dialog.dismiss();
            if (cb != null) cb.onPick(pos);
        });

        btnCancel.setOnClickListener(x -> dialog.dismiss());
        dialog.show();
    }
}
