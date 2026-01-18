package com.example.smartsenior.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
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
                    "popupOverlay == null – sprawdź czy layout zawiera " +
                            "<include layout=\"@layout/view_result_popup\" android:id=\"@+id/popupOverlay\" />"
            );
        }

        titleText = overlay.findViewById(R.id.titleText);
        popupList = overlay.findViewById(R.id.popupList);
        btnOk = overlay.findViewById(R.id.btnOk);

        overlay.setOnClickListener(v -> hideInternal());
        btnOk.setOnClickListener(v -> hideInternal());

        overlay.setVisibility(View.GONE);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
    }

    public void show(boolean isCorrect, String explanation, String buttonText) {
        if (isCorrect) {
            titleText.setText("Poprawna odpowiedź!");
        } else {
            titleText.setText("Niepoprawna odpowiedź");
        }

        popupList.removeAllViews();

        TextView explanationView = new TextView(context);
        explanationView.setText(explanation);
        explanationView.setTextSize(16);
        explanationView.setTextColor(Color.parseColor("#374151"));
        explanationView.setGravity(Gravity.CENTER);
        explanationView.setPadding(dpToPx(16), dpToPx(8), dpToPx(16), dpToPx(8));
        explanationView.setTypeface(
                ResourcesCompat.getFont(context, R.font.montserrat_regular),
                Typeface.NORMAL
        );
        explanationView.setLineSpacing(dpToPx(2), 1.1f);

        popupList.addView(explanationView);

        btnOk.setText(buttonText != null && !buttonText.isEmpty() ? buttonText : "OK");

        showOverlay();
    }

    public void showInfo(String title, String message, String buttonText) {
        titleText.setText(title);

        popupList.removeAllViews();

        TextView messageView = new TextView(context);
        messageView.setText(message);
        messageView.setTextSize(16);
        messageView.setTextColor(Color.parseColor("#374151"));
        messageView.setGravity(Gravity.CENTER);
        messageView.setPadding(dpToPx(16), dpToPx(8), dpToPx(16), dpToPx(8));
        messageView.setTypeface(
                ResourcesCompat.getFont(context, R.font.montserrat_regular),
                Typeface.NORMAL
        );
        messageView.setLineSpacing(dpToPx(2), 1.1f);

        popupList.addView(messageView);

        btnOk.setText(buttonText != null && !buttonText.isEmpty() ? buttonText : "OK");

        showOverlay();
    }

    private void showOverlay() {
        overlay.setAlpha(0f);
        overlay.setVisibility(View.VISIBLE);
        overlay.animate()
                .alpha(1f)
                .setDuration(200)
                .start();
    }


    private void hideInternal() {
        overlay.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction(() -> {
                    overlay.setVisibility(View.GONE);
                    if (onDismissListener != null) {
                        onDismissListener.onDismiss();
                        onDismissListener = null;
                    }
                })
                .start();
    }


    public void hide() {
        hideInternal();
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}