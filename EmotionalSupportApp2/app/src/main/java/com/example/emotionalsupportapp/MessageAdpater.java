package com.example.emotionalsupportapp;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class MessageAdpater extends RecyclerView.Adapter<MessageAdpater.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private ArrayList<String> chats;
    private Context mContext;


    public MessageAdpater(ArrayList<String> chats, Context mContext){
            this.chats = chats;
            this.mContext = mContext;
    }

    @NonNull
    @Override
    public MessageAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent,false);
            return new MessageAdpater.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent,false);
            return new MessageAdpater.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdpater.ViewHolder holder, int position) {
        String chat = chats.get(position);
        holder.show_message.setText(chat);
    }

    @Override
    public int getItemCount() {
            return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView show_message;

        ViewHolder(View itemView){
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return MSG_TYPE_RIGHT;
    }
}

