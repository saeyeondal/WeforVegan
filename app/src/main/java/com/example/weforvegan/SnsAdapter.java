package com.example.weforvegan;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//menu_item을 recyclerView에 넣기 위한 adapter 정의
public class SnsAdapter extends RecyclerView.Adapter<SnsAdapter.ViewHolder> implements OnMenuItemClickListener_sns {
    public static ArrayList<Menu> items = new ArrayList<Menu>();

    public interface OnItemClickLisener{
        void onItemClick(View v, int posision);
    }

    private OnMenuItemClickListener_sns mListener = null;

    public void setOnItemClickListener(OnMenuItemClickListener_sns listener){
        this.mListener = listener;
    }

    @Override
    public void onItemClick(SnsAdapter.ViewHolder holder, View view, int position) {
        if(mListener != null)
            mListener.onItemClick(holder, view, position);
    }

    @NonNull
    @Override
    public SnsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.menu_item, viewGroup, false);

        return new SnsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SnsAdapter.ViewHolder viewHolder, int position) {
        Menu item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Menu item){
        items.add(item);
    }

    public void setItems(ArrayList<Menu> items){
        this.items = items;
    }

    public Menu getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Menu item){
        items.set(position, item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView menu, source;

        public ViewHolder(View itemView) {
            super(itemView);

            menu = itemView.findViewById(R.id.menu_name);
            source = itemView.findViewById(R.id.menu_source);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(SnsAdapter.ViewHolder.this, view, position);
                        }
                    }
                }
            });
        }

        public void setItem(Menu item) {
            menu.setText(item.getMenu());
            source.setText(item.getSource());
        }
    }
}

