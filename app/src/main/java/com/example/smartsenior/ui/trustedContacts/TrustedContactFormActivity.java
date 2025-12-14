package com.example.smartsenior.ui.trustedContacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class TrustedContactFormActivity extends AppCompatActivity {

    private String id;
    private String photoUri = "";

    private TextView tvTitle, tvInitials;
    private ImageView imgAvatar;
    private MaterialButton btnPickPhoto;
    private com.google.android.material.textfield.TextInputEditText etName, etPhone;

    private final ActivityResultLauncher<String[]> pickPhotoLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri == null) return;

                // zapamiętaj dostęp do URI na stałe
                try {
                    getContentResolver().takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );
                } catch (Exception ignored) {}

                photoUri = uri.toString();

                try {
                    imgAvatar.setImageURI(uri);
                } catch (Exception e) {
                    photoUri = "";
                    imgAvatar.setImageDrawable(null);
                }

                refreshPhotoUi();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contact_form);

        TextView btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvInitials = findViewById(R.id.tvInitials);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnPickPhoto = findViewById(R.id.btnPickPhoto);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        MaterialButton btnSave = findViewById(R.id.btnSave);
        MaterialButton btnCancel = findViewById(R.id.btnCancel);

        btnBack.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());

        String mode = getIntent().getStringExtra("mode");
        id = getIntent().getStringExtra("id");

        if ("edit".equals(mode)) {
            tvTitle.setText("Edytuj kontakt");
            etName.setText(getIntent().getStringExtra("name"));
            etPhone.setText(getIntent().getStringExtra("phone"));

            String p = getIntent().getStringExtra("photoUri");
            if (p != null) photoUri = p;

            if (photoUri != null && !photoUri.isEmpty()) {
                try {
                    imgAvatar.setImageURI(Uri.parse(photoUri));
                } catch (Exception e) {
                    photoUri = "";
                    imgAvatar.setImageDrawable(null);
                }
            }
        } else {
            tvTitle.setText("Dodaj kontakt");
        }

        // przycisk: dodaj / zmień zdjęcie
        btnPickPhoto.setOnClickListener(v -> pickPhotoLauncher.launch(new String[]{"image/*"}));

        // długie przytrzymanie avatara = usuń zdjęcie (senior-friendly)
        imgAvatar.setOnLongClickListener(v -> {
            if (photoUri == null || photoUri.isEmpty()) return true;
            photoUri = "";
            imgAvatar.setImageDrawable(null);
            Toast.makeText(this, "Zdjęcie usunięte", Toast.LENGTH_SHORT).show();
            refreshPhotoUi();
            return true;
        });

        // inicjał podgląd na żywo
        etName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { updateInitialsPreview(); }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnSave.setOnClickListener(v -> {
            String name = etName.getText() == null ? "" : etName.getText().toString().trim();
            String phone = etPhone.getText() == null ? "" : etPhone.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Podaj nazwę kontaktu.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (phone.isEmpty()) {
                Toast.makeText(this, "Podaj numer telefonu.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent out = new Intent();
            out.putExtra("id", id);
            out.putExtra("name", name);
            out.putExtra("phone", phone);
            out.putExtra("photoUri", photoUri == null ? "" : photoUri);
            setResult(RESULT_OK, out);
            finish();
        });

        // NA KONIEC – ustaw UI poprawnie
        refreshPhotoUi();
    }

    private void refreshPhotoUi() {
        boolean hasPhoto = photoUri != null && !photoUri.isEmpty();

        if (hasPhoto) {
            imgAvatar.setVisibility(View.VISIBLE);
            tvInitials.setVisibility(View.GONE);
            btnPickPhoto.setText("Zmień zdjęcie");
        } else {
            imgAvatar.setVisibility(View.GONE);
            tvInitials.setVisibility(View.VISIBLE);
            btnPickPhoto.setText("Dodaj zdjęcie (opcjonalnie)");
            updateInitialsPreview();
        }
    }

    private void updateInitialsPreview() {
        // jeśli jest zdjęcie -> inicjał nieważny
        if (photoUri != null && !photoUri.isEmpty()) return;

        String name = etName.getText() == null ? "" : etName.getText().toString().trim();
        String initials = name.isEmpty() ? "?" : name.substring(0, 1).toUpperCase();
        tvInitials.setText(initials);
    }
}
