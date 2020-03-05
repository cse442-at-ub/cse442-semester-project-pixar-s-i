package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HighFiveVolunteerFoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_volunteer_found);
    }

    public void rateVolunteer(View view){
        Intent rate = new Intent(this, HighFiveRatingActivity.class);
        startActivity(rate);
    }
}
