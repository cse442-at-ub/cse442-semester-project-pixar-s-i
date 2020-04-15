package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.emotionalsupportapp.Highfive.HighFiveActivity;
import com.example.emotionalsupportapp.Hug.HugActivity;

public class MainActivity extends AppCompatActivity {

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = getIntent().getStringExtra("EXTRA_USER_ID");
    }

    /*
     * Changes to the main high five page
     * should be used to change to the high five page
     * Called when high five button is clicked
     */
    public void goToHighFivePage(View view){
        Intent intent = new Intent(this, HighFiveActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main hug page
     * should be used to change to the hug page
     * Called when hug button is clicked
     */
    public void goToHugPage(View view){
        Intent intent = new Intent(this, HugActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Motivation page
     * should be used to change to the Motivation page
     * Called when Motivation button is clicked
     */
    public void goToMotivationPage(View view){
        Intent intent = new Intent(this,MotivationActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Talk page
     * should be used to change to the Talk page
     * Called when talk button is clicked
     */
    public void goToTalkPage(View view){
        Intent intent = new Intent(this,TalkActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Connect page
     * should be used to change to the Connect page
     * Called when Connect button is clicked
     */
    public void goToConnectPage(View view){
        Intent intent = new Intent(this,ConnectActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Profile page
     * should be used to change to the Profile page
     * Called when Profile button is clicked
     */
    public void goToProfilePage(View view){
        Intent intent = new Intent(this,profilePage.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
}
