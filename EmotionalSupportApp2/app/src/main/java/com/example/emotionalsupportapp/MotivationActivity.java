package com.example.emotionalsupportapp;

import android.app.Activity;
import android.os.Bundle;

public class MotivationActivity extends Activity {
    int userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motivationlayout);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = 1;
    }
}