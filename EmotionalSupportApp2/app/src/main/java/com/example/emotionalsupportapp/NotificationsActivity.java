package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class NotificationsActivity extends AppCompatActivity {
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = 1;
    }

    public void goToHighFiveNotifications(View view) {
    }

    public void goToHugNotifications(View view) {
    }

    public void goToMotivationNotifications(View view) {
    }

    public void goToTalkNotifications(View view) {
    }

    public void goToConnectNotifications(View view) {
    }
}
