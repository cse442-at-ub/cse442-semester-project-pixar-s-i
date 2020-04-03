package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<String> topFiveVolunteers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        topFiveVolunteers = new ArrayList<>();
        getTopFiveVolunteers();
    }

    private void getTopFiveVolunteers(){
//        topFiveVolunteers.add("Volunteer1");
//        topFiveVolunteers.add("Volunteer2");
//        topFiveVolunteers.add("Volunteer3");
//        topFiveVolunteers.add("Volunteer4");
//        topFiveVolunteers.add("Volunteer5");
//
//        userAdapter = new UserAdapter(topFiveVolunteers, this);
//        recyclerView.setAdapter(userAdapter);
    }
}
