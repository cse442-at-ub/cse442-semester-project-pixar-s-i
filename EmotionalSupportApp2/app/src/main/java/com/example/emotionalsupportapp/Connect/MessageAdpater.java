package com.example.emotionalsupportapp.Connect;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.emotionalsupportapp.R;

public class MessageAdpater extends RecyclerView.Adapter<MessageAdpater.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private String userId;
    private ArrayList<Chat> chats;
    private Context mContext;


    public MessageAdpater(String userId, ArrayList<Chat> chats, Context mContext){
        this.userId = userId;
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
        Chat chat = chats.get(position);
        holder.show_message.setText(chat.getMessage());
        if(chat.getSenderId().equals(userId)){
            if (chat.getHasSeen().equals("0")) {
                holder.has_seen.setText("unseen");
            }
        }
    }

    @Override
    public int getItemCount() {
            return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView show_message;
        TextView has_seen;

        ViewHolder(View itemView){
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            has_seen = itemView.findViewById(R.id.has_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (userId.equals(chats.get(position).getSenderId())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}

