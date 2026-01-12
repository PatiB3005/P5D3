package com.example.smartsenior.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsenior.R;
import com.google.android.material.button.MaterialButton;

public class ResultPopup {

    public interface OnDismissListener {
        void onDismiss();
    }

    private final View overlay;
    private final TextView titleText;
    private final LinearLayout popupList;
    private final MaterialButton btnOk;
    private final Context context;
    private OnDismissListener onDismissListener;

    public ResultPopup(Activity activity) {
        context = activity;

        overlay = activity.findViewById(R.id.popupOverlay);
        if (overlay == null) {
            throw new IllegalStateException(
                    "popupOverlay == null – sprawdź czy activity_fake_news_quiz.xml zawiera " +
                            "<include layout=\"@layout/view_result_popup\" android:id=\"@+id/popupOverlay\" />"
            );
        }

        titleText = overlay.findViewById(R.id.titleText);
        popupList = overlay.findViewById(R.id.popupList);
        btnOk = overlay.findViewById(R.id.btnOk);

        overlay.setOnClickListener(v -> hideInternal());
        btnOk.setOnClickListener(v -> hideInternal());

        // Ukryj popup na start
        overlay.setVisibility(View.GONE);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
    }


    public void show(boolean isCorrect, String explanation, String buttonText) {
        // Ustaw tytuł i kolor w zależności od poprawności odpowiedzi
        if (isCorrect) {
            titleText.setText("Poprawna odpowiedź!");
        } else {
            titleText.setText("Niepoprawna odpowiedź");
        }

        // Wyczyść poprzednie elementy z listy
        popupList.removeAllViews();

        // Dodaj wyjaśnienie
        TextView explanationView = new TextView(context);
        explanationView.setText(explanation);
        explanationView.setTextSize(16);
        explanationView.setTextColor(Color.parseColor("#374151"));
        explanationView.setGravity(Gravity.CENTER);
        explanationView.setPadding(
                dpToPx(16),
                dpToPx(8),
                dpToPx(16),
                dpToPx(8)
        );
        popupList.addView(explanationView);

        // Ustaw tekst przycisku
        if (buttonText != null && !buttonText.isEmpty()) {
            btnOk.setText(buttonText);
        } else {
            btnOk.setText("OK");
        }

        // Pokaż overlay z animacją fade in
        overlay.setAlpha(0f);
        overlay.setVisibility(View.VISIBLE);
        overlay.animate()
                .alpha(1f)
                .setDuration(200)
                .start();
    }

    /**
     * Ukrywa popup i wywołuje listener
     */
    private void hideInternal() {
        // Animacja fade out
        overlay.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction(() -> {
                    overlay.setVisibility(View.GONE);
                    if (onDismissListener != null) {
                        onDismissListener.onDismiss();
                        onDismissListener = null; // Reset listenera
                    }
                })
                .start();
    }

    /**
     * Publiczna metoda do ukrywania popupu
     */
    public void hide() {
        hideInternal();
    }

    /**
     * Konwertuje dp na piksele
     */
    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}