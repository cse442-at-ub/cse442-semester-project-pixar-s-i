package com.example.emotionalsupportapp.Hug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;

public class HugActivity extends AppCompatActivity {

    NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
  
        notificationManager = NotificationManagerCompat.from(this);

    }

    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }

    public void beginHughVolunteerSearch(View view){
        Intent searchHugVolunteer = new Intent(this, HugRequestActivity.class);
        startActivity(searchHugVolunteer);
    }
}
