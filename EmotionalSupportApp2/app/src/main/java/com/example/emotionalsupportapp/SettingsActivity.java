package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {
    int idNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        idNum = Integer.getInteger(sessionId);
    }

    public void goToNotifications(View view) {
        Intent intent = new Intent(this, NotificationsActivity.class);
        intent.putExtra("EXTRA_USER_ID", idNum);
        startActivity(intent);
    }

    public void goToHistory(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("EXTRA_USER_ID", idNum);
        startActivity(intent);
    }
}
