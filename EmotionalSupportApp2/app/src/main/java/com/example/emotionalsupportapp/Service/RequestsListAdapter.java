package com.example.emotionalsupportapp.Service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotionalsupportapp.Member.Profile.User;
import com.example.emotionalsupportapp.R;

import java.util.List;

public class RequestsListAdapter extends RecyclerView.Adapter<RequestsListAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public RequestsListAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_listview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsListAdapter.ViewHolder holder, int position) {
       User person = userList.get(position);
        holder.textUsername.setText(person.getUsername());
        holder.textFirstName.setText("First Name");
        holder.textLastName.setText("Last Name");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textUsername,textFirstName,textLastName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.username);
            textFirstName = itemView.findViewById(R.id.first_name);
            textLastName = itemView.findViewById(R.id.last_name);

        }
    }
}
