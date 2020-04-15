package com.example.emotionalsupportapp.Service;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotionalsupportapp.Connect.MessageActivity;
import com.example.emotionalsupportapp.R;
import com.example.emotionalsupportapp.Volunteer;

import java.util.ArrayList;

public class TopFiveVolunteerAdapter extends RecyclerView.Adapter<TopFiveVolunteerAdapter.ViewHolder>  {
    private ArrayList<Volunteer> topFiveVolunteers;
    private Context mContext;

    public TopFiveVolunteerAdapter(ArrayList<Volunteer> topFiveVolunteers, Context mContext){
        this.topFiveVolunteers = topFiveVolunteers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TopFiveVolunteerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent,false);
        return new TopFiveVolunteerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopFiveVolunteerAdapter.ViewHolder holder, int position) {
        final Volunteer volunteer = topFiveVolunteers.get(position);
        holder.username.setText(volunteer.getVolunteerName());
        holder.user_id.setText("");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("VOLUNTEER", volunteer);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topFiveVolunteers.size();
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
