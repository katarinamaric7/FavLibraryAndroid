package com.example.myproject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private ArrayList<String> favoriteBooksList;

    public FavoritesAdapter(ArrayList<String> favoriteBooksList) {
        this.favoriteBooksList = favoriteBooksList;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_book_item, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        String bookTitle = favoriteBooksList.get(position);
        holder.titleTextView.setText(bookTitle);
    }

    @Override
    public int getItemCount() {
        return favoriteBooksList.size();
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.favoriteBookTitle);
        }
    }
}

