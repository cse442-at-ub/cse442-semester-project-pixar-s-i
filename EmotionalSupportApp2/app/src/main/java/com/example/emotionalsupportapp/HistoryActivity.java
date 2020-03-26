package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HistoryActivity extends AppCompatActivity {
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = 1;
    }
}
