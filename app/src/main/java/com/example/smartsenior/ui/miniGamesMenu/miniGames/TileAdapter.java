package com.example.smartsenior.ui.miniGamesMenu.miniGames;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsenior.R;

import java.util.List;

public class TileAdapter extends RecyclerView.Adapter<TileAdapter.TileVH> {

    public interface OnTileClickListener {
        void onTileClick(TileItem item);
    }

    private final List<TileItem> items;
    private final OnTileClickListener listener;

    public TileAdapter(List<TileItem> items, OnTileClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TileVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tile, parent, false);
        return new TileVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TileVH holder, int position) {
        TileItem item = items.get(position);
        holder.tv.setText(item.text);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTileClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int indexOf(TileItem item) {
        return items.indexOf(item);
    }

    public void removeAt(int position) {
        if (position < 0 || position >= items.size()) return;
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void insertAt(int position, TileItem item) {
        if (position < 0) position = 0;
        if (position > items.size()) position = items.size();
        items.add(position, item);
        notifyItemInserted(position);
    }

    static class TileVH extends RecyclerView.ViewHolder {
        TextView tv;
        TileVH(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvTile);
        }
    }
}
