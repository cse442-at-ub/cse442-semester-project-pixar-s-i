package com.example.emotionalsupportapp;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import static com.example.emotionalsupportapp.EmotionalSupport.CHANNEL_1_ID;

public class HighFiveActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five);
        notificationManager = NotificationManagerCompat.from(this);
    }

    public void returnToMain(View view){
        //Notification is made and title and text is set as below for now with a icon, .build to finish the setup
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notifications_24)
                .setContentTitle("User request High Five")
                .setContentText("Click to Confirm HighFive Request")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .build();
        notificationManager.notify(1,notification);
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }

    public void beginHighFiveSearch(View view){
        Intent highFiveSearch = new Intent(this, HighFiveRequestActivity.class);
        startActivity(highFiveSearch);
    }

}