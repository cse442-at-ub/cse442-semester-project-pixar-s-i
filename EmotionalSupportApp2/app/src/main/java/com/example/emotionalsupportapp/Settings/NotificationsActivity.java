package com.example.emotionalsupportapp.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.emotionalsupportapp.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationsActivity extends AppCompatActivity {
    String userID;
    private int highFive = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = getIntent().getStringExtra("EXTRA_USER_ID");;
    }

    public void goToHighFiveNotifications(View view) {
        Button btn = findViewById(view.getId());
//        if() {
//            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//            FirebaseMessaging.getInstance().unsubscribeFromTopic("High_Five");
//        }
//        else{
//            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            FirebaseMessaging.getInstance().subscribeToTopic("High_Five");
//        }

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
