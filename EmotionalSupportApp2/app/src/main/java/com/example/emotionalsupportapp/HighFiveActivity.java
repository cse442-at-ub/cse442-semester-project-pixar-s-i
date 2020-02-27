package com.example.emotionalsupportapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HighFiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five);
    }

    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);

    }

    public void beginHighFiveSearch(View view){
        Intent highFiveSearch = new Intent(this, HighFiveRequestActivity.class);
        startActivity(highFiveSearch);
    }


}