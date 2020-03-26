package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TalkActivity extends AppCompatActivity {
    int idNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        idNum = Integer.getInteger(sessionId);
    }
    /*
     * Changes to the main high five page
     * should be used to change to the high five page
     * Called when high five button is clicked
     */
    public void goToHomePage(View view){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("EXTRA_USER_ID", idNum);
        startActivity(intent);


    }
}