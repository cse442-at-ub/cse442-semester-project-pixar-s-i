package com.example.emotionalsupportapp.Service;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.Connect.Friend;
import com.example.emotionalsupportapp.Connect.MessageActivity;
import com.example.emotionalsupportapp.R;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Friend current_friend = friends.get(position);
        String name_id = current_friend.getFriendName() + " #" + current_friend.getFriendId();
        holder.username.setText(name_id);

        String getLastMessageURL = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getLastMessage.php/" +
                "?user_id=" + current_friend.getUserId() + "&receiver_id=" + current_friend.getFriendId();
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getLastMessageURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject re = response.getJSONObject("response");
                    String s = re.getString("message");
                    current_friend.setLastMessage(s);
                    holder.user_id.setText(current_friend.getLastMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR:", "Error on Volley: " + error.toString());
            }
        });
        reqQueue.add(jsonObjectRequest);



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
