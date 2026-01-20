package com.example.smartsenior.ui.trustedContacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TrustedContactFormActivity extends AppCompatActivity {

    private String id;
    private String photoUri = "";

    private TextView tvTitle, tvInitials;
    private ImageView imgAvatar;
    private MaterialButton btnPickPhoto;

    private TextInputEditText etName, etPhone;
    private TextInputLayout tilName, tilPhone;

    private final ActivityResultLauncher<String[]> pickPhotoLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri == null) return;

                try {
                    getContentResolver().takePersistableUriPermission(
                            uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
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

        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light_NoActionBar);
        setContentView(R.layout.activity_trusted_contact_form);

        /* =======================
           TOOLBAR – STRZAŁKA
           ======================= */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        /* =======================
           WIDOKI
           ======================= */
        tvTitle = findViewById(R.id.tvTitle);
        tvInitials = findViewById(R.id.tvInitials);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnPickPhoto = findViewById(R.id.btnPickPhoto);

        tilName = findViewById(R.id.tilName);
        tilPhone = findViewById(R.id.tilPhone);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);

        MaterialButton btnSave = findViewById(R.id.btnSave);
        MaterialButton btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(v -> finish());

        /* =======================
           TELEFON – 9 CYFR
           ======================= */
        etPhone.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        etPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        etPhone.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(9) });

        /* =======================
           TRYB: DODAJ / EDYTUJ
           ======================= */
        String mode = getIntent().getStringExtra("mode");
        id = getIntent().getStringExtra("id");

        if ("edit".equals(mode)) {
            tvTitle.setText("Edytuj kontakt");
            etName.setText(getIntent().getStringExtra("name"));

            String incomingPhone = getIntent().getStringExtra("phone");
            etPhone.setText(PhoneUtils.national9FromAny(incomingPhone));

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

        /* =======================
           ZDJĘCIE
           ======================= */
        btnPickPhoto.setOnClickListener(v ->
                pickPhotoLauncher.launch(new String[]{"image/*"})
        );

        imgAvatar.setOnLongClickListener(v -> {
            if (photoUri == null || photoUri.isEmpty()) return true;
            photoUri = "";
            imgAvatar.setImageDrawable(null);
            Toast.makeText(this, "Zdjęcie usunięte", Toast.LENGTH_SHORT).show();
            refreshPhotoUi();
            return true;
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateInitialsPreview();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        /* =======================
           ZAPIS
           ======================= */
        btnSave.setOnClickListener(v -> {
            String name = etName.getText() == null ? "" : etName.getText().toString().trim();
            String phone9 = etPhone.getText() == null ? "" : etPhone.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Podaj nazwę kontaktu.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phone9.isEmpty()) {
                Toast.makeText(this, "Podaj 9 cyfr numeru telefonu.", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullPhone = PhoneUtils.normalizeToPL(phone9);
            if (fullPhone == null) {
                Toast.makeText(this, "Numer musi mieć dokładnie 9 cyfr.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent out = new Intent();
            out.putExtra("id", id == null ? "" : id);
            out.putExtra("name", name);
            out.putExtra("phone", fullPhone);
            out.putExtra("photoUri", photoUri == null ? "" : photoUri);
            setResult(RESULT_OK, out);
            finish();
        });

        refreshPhotoUi();
    }

    /* =======================
       UI – ZDJĘCIE / INICJAŁY
       ======================= */
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
        if (photoUri != null && !photoUri.isEmpty()) return;

        String name = etName.getText() == null ? "" : etName.getText().toString().trim();
        String initials = name.isEmpty() ? "?" : name.substring(0, 1).toUpperCase();
        tvInitials.setText(initials);
    }
}
