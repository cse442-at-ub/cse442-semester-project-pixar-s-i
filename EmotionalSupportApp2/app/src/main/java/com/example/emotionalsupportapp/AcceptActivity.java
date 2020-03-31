package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class AcceptActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VolunteerAdapter volunteerAdapter;
    private ArrayList<String> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users = new ArrayList<>();
        getTopFiveVolunteers();
    }

    private void getTopFiveVolunteers(){
        users.add("user1");
        users.add("user2");
        users.add("user3");

        volunteerAdapter = new VolunteerAdapter(users, this);
        recyclerView.setAdapter(volunteerAdapter);
    }
}
