package com.example.emotionalsupportapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug);
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