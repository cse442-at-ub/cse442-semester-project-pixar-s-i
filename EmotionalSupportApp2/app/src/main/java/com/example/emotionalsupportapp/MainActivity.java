package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Button for High Five
        Button highFiveBtn = findViewById(R.id.highFiveBtn);

    }


    public void changePage(View view){

    }
}
