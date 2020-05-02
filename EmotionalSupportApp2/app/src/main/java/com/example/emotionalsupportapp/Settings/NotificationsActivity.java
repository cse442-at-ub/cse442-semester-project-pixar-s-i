package com.example.emotionalsupportapp.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.emotionalsupportapp.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationsActivity extends AppCompatActivity {
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = getIntent().getStringExtra("EXTRA_USER_ID");;
    }

    public void goToHighFiveNotifications(View view) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("High_Five");

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
