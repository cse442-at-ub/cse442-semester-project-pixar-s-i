package com.example.emotionalsupportapp.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.emotionalsupportapp.R;

public class SettingsActivity extends AppCompatActivity {
    String idNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        idNum = getIntent().getStringExtra("EXTRA_USER_ID");
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
