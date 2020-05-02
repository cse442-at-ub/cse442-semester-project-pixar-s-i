package com.example.emotionalsupportapp.Hug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.Member.Registration.LoginActivity;
import com.example.emotionalsupportapp.R;

public class HugRatingActivity extends AppCompatActivity {

    private String userID;
    private String volunteerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug_rating);

        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            userID = b.getString("EXTRA_USER_ID");
            volunteerID = b.getString("EXTRA_VOLUNTEER_ID");
        }else{
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
    }

    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        returnToMainIntent.putExtra("EXTRA_USER_ID",userID);
        startActivity(returnToMainIntent);
    }

    @Override
    public void onBackPressed() {
        Intent goToHug = new Intent(this, HugActivity.class);
        goToHug.putExtra("EXTRA_USER_ID",userID);
        startActivity(goToHug);
    }
}
