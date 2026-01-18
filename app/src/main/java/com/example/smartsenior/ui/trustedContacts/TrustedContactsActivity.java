package com.example.smartsenior.ui.trustedContacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.UUID;

public class TrustedContactsActivity extends AppCompatActivity {

    private ArrayList<TrustedContact> contacts;
    private TrustedAdapter adapter;
    private TextView tvEmpty;

    private final ActivityResultLauncher<Intent> formLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() != RESULT_OK || result.getData() == null) return;

                String id = result.getData().getStringExtra("id");
                String name = result.getData().getStringExtra("name");
                String phone = result.getData().getStringExtra("phone");
                String photoUri = result.getData().getStringExtra("photoUri");

                if (id == null || id.trim().isEmpty()) return;

                String normalizedPhone = PhoneUtils.normalizeToPL(phone);
                if (normalizedPhone == null) {
                    Toast.makeText(this, "Nieprawidłowy numer telefonu. Nie zapisano.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int idx = findIndexById(id);
                if (idx >= 0) {
                    TrustedContact c = contacts.get(idx);
                    c.name = (name == null ? "" : name);
                    c.phone = normalizedPhone;
                    c.photoUri = (photoUri == null ? "" : photoUri);
                } else {
                    TrustedContact c = new TrustedContact();
                    c.id = id;
                    c.name = (name == null ? "" : name);
                    c.phone = normalizedPhone;
                    c.photoUri = (photoUri == null ? "" : photoUri);
                    contacts.add(c);
                }

                TrustedContactsStorage.save(this, contacts);
                adapter.notifyDataSetChanged();
                updateEmpty();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contacts);

        TextView btnBack = findViewById(R.id.btnBack);
        ConstraintLayout addNewRow = findViewById(R.id.addNewRow);
        RecyclerView rvTrusted = findViewById(R.id.rvTrusted);
        tvEmpty = findViewById(R.id.tvEmpty);

        btnBack.setOnClickListener(v -> finish());

        contacts = TrustedContactsStorage.load(this);

        adapter = new TrustedAdapter(contacts, new TrustedAdapter.Callbacks() {
            @Override public void onEdit(TrustedContact c) { openFormEdit(c); }
            @Override public void onDelete(TrustedContact c) { confirmDelete(c); }
            @Override public void onCall(TrustedContact c) { dialNumber(TrustedContactsActivity.this, c.phone); }
        });

        rvTrusted.setLayoutManager(new LinearLayoutManager(this));
        rvTrusted.setAdapter(adapter);

        addNewRow.setOnClickListener(v -> {
            if (contacts.size() >= 5) {
                Toast.makeText(this, "Możesz dodać maksymalnie 5 kontaktów.", Toast.LENGTH_SHORT).show();
                return;
            }
            openFormAdd();
        });

        updateEmpty();
    }

    private void updateEmpty() {
        if (tvEmpty == null) return;
        tvEmpty.setVisibility(contacts == null || contacts.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private int findIndexById(String id) {
        for (int i = 0; i < contacts.size(); i++) {
            TrustedContact c = contacts.get(i);
            if (c != null && c.id != null && c.id.equals(id)) return i;
        }
        return -1;
    }

    private void openFormAdd() {
        Intent i = new Intent(this, TrustedContactFormActivity.class);
        i.putExtra("mode", "add");
        i.putExtra("id", UUID.randomUUID().toString());
        formLauncher.launch(i);
    }

    private void openFormEdit(TrustedContact c) {
        Intent i = new Intent(this, TrustedContactFormActivity.class);
        i.putExtra("mode", "edit");
        i.putExtra("id", c.id);
        i.putExtra("name", c.name);
        i.putExtra("phone", c.phone);     // tu jest +48... ale form pokaże tylko 9 cyfr
        i.putExtra("photoUri", c.photoUri);
        formLauncher.launch(i);
    }

    private void confirmDelete(TrustedContact c) {
        new AlertDialog.Builder(this)
                .setTitle("Usunąć kontakt?")
                .setMessage("Czy na pewno chcesz usunąć: " + (c.name == null ? "" : c.name) + "?")
                .setPositiveButton("Usuń", (d, w) -> {
                    int idx = findIndexById(c.id);
                    if (idx >= 0) contacts.remove(idx);
                    TrustedContactsStorage.save(this, contacts);
                    adapter.notifyDataSetChanged();
                    updateEmpty();
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private static void dialNumber(Context ctx, String phone) {
        String normalized = PhoneUtils.normalizeToPL(phone);
        if (normalized == null) {
            Toast.makeText(ctx, "Nieprawidłowy numer telefonu.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + normalized));
        try {
            ctx.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(ctx, "Nie można otworzyć telefonu.", Toast.LENGTH_SHORT).show();
        }
    }

    // ===== Adapter =====
    static class TrustedAdapter extends RecyclerView.Adapter<TrustedAdapter.VH> {

        interface Callbacks {
            void onEdit(TrustedContact c);
            void onDelete(TrustedContact c);
            void onCall(TrustedContact c);
        }

        private final ArrayList<TrustedContact> items;
        private final Callbacks cb;

        TrustedAdapter(ArrayList<TrustedContact> items, Callbacks cb) {
            this.items = items;
            this.cb = cb;
        }

        static class VH extends RecyclerView.ViewHolder {
            final TextView tvInitials, tvName, tvPhone, tvMenu;
            final ImageView imgAvatar;
            final MaterialCardView chipWrap;
            final MaterialButton btnCall;

            VH(@NonNull View itemView) {
                super(itemView);
                tvInitials = itemView.findViewById(R.id.tvInitials);
                tvName = itemView.findViewById(R.id.tvName);
                tvPhone = itemView.findViewById(R.id.tvPhone);
                tvMenu = itemView.findViewById(R.id.tvMenu);
                imgAvatar = itemView.findViewById(R.id.imgAvatar);
                chipWrap = itemView.findViewById(R.id.chipWrap);
                btnCall = itemView.findViewById(R.id.btnCall);
            }
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trusted_contact, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            TrustedContact item = items.get(position);

            String name = item.name == null ? "" : item.name;
            String phone = item.phone == null ? "" : item.phone;

            holder.tvName.setText(name);
            holder.tvPhone.setText(phone);

            // initials
            String initials = "?";
            if (!name.trim().isEmpty()) initials = name.trim().substring(0, 1).toUpperCase();
            holder.tvInitials.setText(initials);

            // photos
            if (item.photoUri != null && !item.photoUri.isEmpty()) {
                try {
                    Uri u = Uri.parse(item.photoUri);
                    holder.imgAvatar.setVisibility(View.VISIBLE);
                    holder.tvInitials.setVisibility(View.GONE);
                    holder.imgAvatar.setImageURI(u);
                } catch (Exception e) {
                    holder.imgAvatar.setImageDrawable(null);
                    holder.imgAvatar.setVisibility(View.GONE);
                    holder.tvInitials.setVisibility(View.VISIBLE);

                    item.photoUri = "";
                    TrustedContactsStorage.save(holder.itemView.getContext(), items);
                }
            } else {
                holder.imgAvatar.setImageDrawable(null);
                holder.imgAvatar.setVisibility(View.GONE);
                holder.tvInitials.setVisibility(View.VISIBLE);
            }

            // Big button: call
            holder.btnCall.setOnClickListener(v -> cb.onCall(item));

            holder.tvPhone.setOnClickListener(v -> cb.onCall(item));

            holder.itemView.setOnClickListener(v -> cb.onEdit(item));

            // menu ⋮
            holder.tvMenu.setOnClickListener(v -> {
                PopupMenu pm = new PopupMenu(v.getContext(), v);
                pm.getMenu().add("Edytuj");
                pm.getMenu().add("Usuń");
                pm.setOnMenuItemClickListener(mi -> {
                    String t = mi.getTitle().toString();
                    if (t.equals("Edytuj")) cb.onEdit(item);
                    else if (t.equals("Usuń")) cb.onDelete(item);
                    return true;
                });
                pm.show();
            });
        }

        @Override
        public int getItemCount() { return items.size(); }
    }
}
