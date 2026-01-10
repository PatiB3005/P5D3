package com.example.smartsenior.ui.miniGamesMenu.miniGames.MemoryGame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsenior.R;

import java.util.List;

public class MemoryBoardAdapter extends RecyclerView.Adapter<MemoryBoardAdapter.CardVH> {

    public interface OnCardClickListener {
        void onCardClick(int position);
    }

    private final List<Card> cards;
    private final OnCardClickListener listener;

    private final int backResId = R.drawable.card_back;

    public MemoryBoardAdapter(List<Card> cards, OnCardClickListener listener) {
        this.cards = cards;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memory_card, parent, false);
        return new CardVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardVH holder, int position) {
        Card c = cards.get(position);

        // obrazek przód/tył
        if (c.isFaceUp() || c.isMatched()) {
            holder.ivCard.setImageResource(c.getImageResId());
        } else {
            holder.ivCard.setImageResource(backResId);
        }

        holder.itemView.setAlpha(c.isMatched() ? 0.45f : 1f);

        holder.itemView.setOnClickListener(v -> {
            if (listener == null) return;

            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            Card clicked = cards.get(pos);
            if (clicked.isMatched() || clicked.isFaceUp()) return;

            listener.onCardClick(pos);
        });
    }

    @Override
    public int getItemCount() {
        return cards == null ? 0 : cards.size();
    }

    public static class CardVH extends RecyclerView.ViewHolder {
        ImageView ivCard;

        public CardVH(@NonNull View itemView) {
            super(itemView);
            ivCard = itemView.findViewById(R.id.ivCard);
        }
    }
}
