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

public class InfoPopup {

    public interface OnDismissListener {
        void onDismiss();
    }

    private final View overlay;
    private final TextView titleText;
    private final LinearLayout popupList;
    private final MaterialButton btnOk;
    private final Context context;
    private OnDismissListener onDismissListener;

    public InfoPopup(Activity activity) {
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

        overlay.setOnClickListener(v -> hide());
        btnOk.setOnClickListener(v -> hide());

        overlay.setVisibility(View.GONE);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
    }


    public void show(String title, String message) {
        show(title, message, "OK");
    }


    public void show(String title, String message, String buttonText) {
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

        overlay.setAlpha(0f);
        overlay.setVisibility(View.VISIBLE);
        overlay.animate()
                .alpha(1f)
                .setDuration(200)
                .start();
    }

    public void hide() {
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


    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}