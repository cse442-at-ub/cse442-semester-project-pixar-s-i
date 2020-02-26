package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*
     * Changes to the main high five page
     * should be used to change to the high five page
     * Called when high five button is clicked
     */
    public void goToHighFivePage(View view){
        Intent intent = new Intent(this,profilePage.class);
        startActivity(intent);
    }
    /*
     * Changes to the main hug page
     * should be used to change to the hug page
     * Called when hug button is clicked
     */
    public void goToHugPage(View view){
        Intent intent = new Intent(this,profilePage.class);
        startActivity(intent);
    }
    /*
     * Changes to the main Motivation page
     * should be used to change to the Motivation page
     * Called when Motivation button is clicked
     */
    public void goToMotivationPage(View view){
        Intent intent = new Intent(this,MotivationActivity.class);
        startActivity(intent);
    }
    /*
     * Changes to the main Talk page
     * should be used to change to the Talk page
     * Called when talk button is clicked
     */
    public void goToTalkPage(View view){
        Intent intent = new Intent(this,profilePage.class);
        startActivity(intent);
    }
    /*
     * Changes to the main Connect page
     * should be used to change to the Connect page
     * Called when Connect button is clicked
     */
    public void goToConnectPage(View view){
        Intent intent = new Intent(this,profilePage.class);
        startActivity(intent);
    }
    /*
     * Changes to the main Profile page
     * should be used to change to the Profile page
     * Called when Profile button is clicked
     */
    public void goToProfilePage(View view){
        Intent intent = new Intent(this,profilePage.class);
        startActivity(intent);
    }
}
