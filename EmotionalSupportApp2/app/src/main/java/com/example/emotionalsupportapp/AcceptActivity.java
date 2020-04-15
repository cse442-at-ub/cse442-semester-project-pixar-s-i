package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AcceptActivity extends AppCompatActivity {
    private String userId;
    private String userName;

    private ArrayList<Volunteer> users;
    private ArrayList<String> hasChatted;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        userId = getIntent().getStringExtra("EXTRA_USER_ID");
        userName = getIntent().getStringExtra("EXTRA_USER_NAME");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        users = new ArrayList<>();
        hasChatted = new ArrayList<>();

        getVolunteerHasChatted();
    }

    private void getVolunteerHasChatted(){
        users.clear();
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getMessages.php/?user_id=" + userId + "&from_volunteer=1";
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray re = response.getJSONArray("response");
                    for (int i = 0; i < re.length(); i++) {
                        JSONObject jsonobject = re.getJSONObject(i);
                        String senderId = jsonobject.getString("senderId");
                        String senderName = jsonobject.getString("senderName");
                        String receiverId = jsonobject.getString("receiverId");
                        String receiverName = jsonobject.getString("receiverName");
                        if (userId.equals(senderId)){
                            if (hasChatted.contains(receiverId)){
                                continue;
                            }
                            hasChatted.add(receiverId);
                            users.add(new Volunteer(senderId, senderName, receiverId, receiverName, "0"));
                        }else if (userId.equals(receiverId)){
                            if (hasChatted.contains(senderId)){
                                continue;
                            }
                            hasChatted.add(senderId);
                            users.add(new Volunteer(receiverId, receiverName, senderId, senderName, "0"));
                        }
                    }
                    TopFiveVolunteerAdapter userAdapter = new TopFiveVolunteerAdapter(users, AcceptActivity.this);
                    recyclerView.setAdapter(userAdapter);
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
    }
}
