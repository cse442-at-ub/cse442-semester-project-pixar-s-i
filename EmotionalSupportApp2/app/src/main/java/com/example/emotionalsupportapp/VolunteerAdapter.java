package com.example.emotionalsupportapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.ViewHolder> {

    private ArrayList<String> users;
    private Context mContext;

    public VolunteerAdapter(ArrayList<String> users, Context mContext){
        this.users = users;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_item, parent,false);
        return new VolunteerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String current_username = users.get(position);
        holder.username.setText(current_username);
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("username", current_username);
                mContext.startActivity(intent);
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, users.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        Button accept;
        Button decline;

        ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            accept = itemView.findViewById(R.id.accept);
            decline = itemView.findViewById(R.id.decline);
        }
    }
}
