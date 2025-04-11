package com.example.myproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TBRAdapter extends RecyclerView.Adapter<TBRAdapter.TBRViewHolder> {

    List<TBRBook> list;

    public TBRAdapter(List<TBRBook> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TBRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        TBRViewHolder vh = new TBRViewHolder(v, list);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TBRViewHolder holder, int position) {
        holder.textViewItem.setText(list.get(position).getNameAndAuthor());
        holder.checkBoxDone.setTag(position);
        holder.checkBoxDone.setChecked(list.get(position).isDone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TBRViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem;
        public CheckBox checkBoxDone;

        public TBRViewHolder(View v, List<TBRBook> list) {
            super(v);
            textViewItem = v.findViewById(R.id.textViewListItem);
            checkBoxDone = v.findViewById(R.id.checkBoxDone);
            checkBoxDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get((Integer) buttonView.getTag()).setDone(isChecked);
                }
            });
        }
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    public void add(TBRBook item){
        list.add(item);
        notifyDataSetChanged();
    }

    public void addData(List<TBRBook> itemList){
       this.list = itemList;
        notifyDataSetChanged();
    }

    public List<TBRBook> getData(){
        return list;
    }
}