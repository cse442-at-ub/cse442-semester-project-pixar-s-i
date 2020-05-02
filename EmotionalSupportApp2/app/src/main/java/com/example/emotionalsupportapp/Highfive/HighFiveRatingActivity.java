package com.example.emotionalsupportapp.Highfive;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emotionalsupportapp.Hug.HugActivity;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.Member.Registration.LoginActivity;
import com.example.emotionalsupportapp.R;

public class HighFiveRatingActivity extends AppCompatActivity {

    private String userID;
    private String volunteerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_rating);

        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            userID = b.getString("EXTRA_USER_ID");
            volunteerID = b.getString("EXTRA_VOLUNTEER_ID");
        }else{
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
    }

    @Override
    public void onBackPressed() {
        Intent goToHighFive = new Intent(this, HighFiveActivity.class);
        goToHighFive.putExtra("EXTRA_USER_ID",userID);
        startActivity(goToHighFive);
    }
    //Updating the user rating and return to the main page
    public void updateRatingandReturnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        returnToMainIntent.putExtra("EXTRA_USER_ID",userID);
        startActivity(returnToMainIntent);
    }
}
