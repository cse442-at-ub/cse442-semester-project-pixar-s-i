package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }

    public void goToHighFiveNotifications(View view) {
        Intent intent = new Intent(this, HighFiveNotificationsActivity.class);
        startActivity(intent);
    }

    public void goToHugNotifications(View view) {
        Intent intent = new Intent(this, HugNotificationsActivity.class);
        startActivity(intent);
    }

    public void goToMotivationNotifications(View view) {
        Intent intent = new Intent(this, MotivationNotificationsActivity.class);
        startActivity(intent);
    }

    public void goToTalkNotifications(View view) {
        Intent intent = new Intent(this, TalkNotificationsActivity.class);
        startActivity(intent);
    }

    public void goToConnectNotifications(View view) {
        Intent intent = new Intent(this, ConnectNotificationsActivity.class);
        startActivity(intent);
    }
}
