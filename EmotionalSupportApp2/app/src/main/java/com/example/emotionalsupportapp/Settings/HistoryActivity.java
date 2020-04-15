package com.example.emotionalsupportapp.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.emotionalsupportapp.R;

public class HistoryActivity extends AppCompatActivity {
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        String userID = getIntent().getStringExtra("EXTRA_USER_ID");
    }
}
