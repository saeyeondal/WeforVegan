package com.example.weforvegan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> implements OnMenuItemClickListener_request{
    public static ArrayList<Request> items = new ArrayList<Request>();

    public interface OnItemClickLisener{
        void onItemClick(View v, int posision);
    }


    private OnMenuItemClickListener_request mListener = null;

    public void setOnItemClickListener(OnMenuItemClickListener_request listener){
        this.mListener = listener;
    }

    @Override
    public void onItemClick(RequestAdapter.ViewHolder holder, View view, int position) {
        if(mListener != null)
            mListener.onItemClick(holder, view, position);
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.request_item, viewGroup, false);

        return new RequestAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder viewHolder, int position) {
        Request item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Request item){
        items.add(item);
    }

    public void setItems(ArrayList<Request> items){
        this.items = items;
    }

    public Request getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Request item){
        items.set(position, item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, state, reply;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            state = itemView.findViewById(R.id.state);
            reply = itemView.findViewById(R.id.request_reply);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(ViewHolder.this, view, position);
                        }
                    }
                }
            });
        }

        public void setItem(Request item) {
            title.setText(item.getTitle());
            state.setText(item.getState());
            reply.setText(item.getReply());
        }
    }
}