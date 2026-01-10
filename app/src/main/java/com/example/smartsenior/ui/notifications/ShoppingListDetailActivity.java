package com.example.smartsenior.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ShoppingListDetailActivity extends AppCompatActivity {

    public static final String EXTRA_LIST_ID = "extra_list_id";

    private ShoppingList shoppingList;
    private ListView listView;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_detail);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());
        topAppBar.setTitle("");

        tvTitle = findViewById(R.id.tvScreenTitle);
        listView = findViewById(R.id.listItems);

        int id = getIntent().getIntExtra(EXTRA_LIST_ID, -1);
        shoppingList = ShoppingListStore.findById(this, id);

        if (shoppingList == null) {
            tvTitle.setText("Lista zakupów");
            return;
        }

        tvTitle.setText(shoppingList.title);

        listView.setAdapter(new ChecklistAdapter());
    }

    private class ChecklistAdapter extends BaseAdapter {
        @Override public int getCount() { return shoppingList.items.size(); }
        @Override public Object getItem(int position) { return shoppingList.items.get(position); }
        @Override public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = LayoutInflater.from(ShoppingListDetailActivity.this)
                        .inflate(R.layout.item_shopping_check_row, parent, false);
            }

            ShoppingItem it = shoppingList.items.get(position);
            CheckBox cb = v.findViewById(R.id.cbItem);

            cb.setOnCheckedChangeListener(null);
            cb.setText(it.text);
            cb.setChecked(it.checked);

            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                it.checked = isChecked;
                ShoppingListStore.update(ShoppingListDetailActivity.this, shoppingList);
            });

            return v;
        }
    }
}
