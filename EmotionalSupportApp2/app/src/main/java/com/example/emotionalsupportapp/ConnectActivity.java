package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConnectActivity extends AppCompatActivity {
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = Integer.getInteger(sessionId);
    }
    /*
     * Changes to the main high five page
     * should be used to change to the high five page
     * Called when high five button is clicked
     */
    public void goToHomePage(View view){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
}