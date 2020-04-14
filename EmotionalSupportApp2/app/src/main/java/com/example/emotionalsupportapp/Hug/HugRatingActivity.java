package com.example.emotionalsupportapp.Hug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;

public class HugRatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug_rating);
    }

    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }
}
