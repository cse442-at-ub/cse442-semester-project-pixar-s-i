package com.example.emotionalsupportapp;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.emotionalsupportapp.EmotionalSupport.CHANNEL_2_ID;

public class HugActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug);
        notificationManager = NotificationManagerCompat.from(this);
    }

    public void returnToMain(View view){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_notifications_24)
                .setContentTitle("User request High Five")
                .setContentText("Click to Confirm HighFive Request")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .build();
        notificationManager.notify(2,notification);
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        returnToMainIntent.putExtra("EXTRA_USER_ID", userID);
        startActivity(returnToMainIntent);
    }

    public void beginHughVolunteerSearch(View view){
        Intent searchHugVolunteer = new Intent(this, HugRequestActivity.class);
        searchHugVolunteer.putExtra("EXTRA_USER_ID", userID);
        startActivity(searchHugVolunteer);
    }
}
