package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.database.AppDatabase;
import com.example.myproject.database.Item;

import java.util.List;

public class MyJournalAdapter extends RecyclerView.Adapter<MyJournalAdapter.MyJournalViewHolder>{
    List<Item> itemList;
    static Context context;


    public MyJournalAdapter(final Context context){
        this.context = context;
        AppDatabase.databaseWriteExecutor.execute(() ->{
            this.itemList = AppDatabase.getInstance(context).itemDao().getAll();
        });
    }
    @NonNull
    @Override
    public MyJournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journal_item, parent, false);
        MyJournalViewHolder vh = new MyJournalViewHolder(v, itemList);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyJournalViewHolder holder, int position) {
        holder.textViewListItem.setText(itemList.get(position).getDescription());
        holder.textViewDate.setText(itemList.get(position).getDate() + " " + itemList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyJournalViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewListItem;
        public TextView textViewDate;

        public MyJournalViewHolder(View v, List<Item> itemList) {
            super(v);
            textViewListItem = v.findViewById(R.id.textViewListItemMJ);
            textViewDate = v.findViewById(R.id.textViewDate);
        }
    }


    public void add(Item item) {
        itemList.add(item);
        notifyDataSetChanged();
    }

}

