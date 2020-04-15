package com.example.emotionalsupportapp.Service;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotionalsupportapp.Connect.Friend;
import com.example.emotionalsupportapp.Connect.MessageActivity;
import com.example.emotionalsupportapp.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<Friend> friends;
    private Context mContext;

    public UserAdapter(ArrayList<Friend> friends, Context mContext){
        this.friends = friends;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent,false);
        return new UserAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Friend current_friend = friends.get(position);
        holder.username.setText(current_friend.getFriendName());
        String friend_id = "#" + current_friend.getFriendId();
        holder.user_id.setText(friend_id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra("FRIEND", current_friend);
            mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView user_id;

        ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            user_id = itemView.findViewById(R.id.user_id);
        }
    }
}
