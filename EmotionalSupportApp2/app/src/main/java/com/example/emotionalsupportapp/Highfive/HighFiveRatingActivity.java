package com.example.emotionalsupportapp.Highfive;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;

public class HighFiveRatingActivity extends AppCompatActivity {

    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_rating);
        userID = getIntent().getExtras().getString("EXTRA_USER_ID");
    }

    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        returnToMainIntent.putExtra("EXTRA_USER_ID",userID);
        startActivity(returnToMainIntent);
    }
}
