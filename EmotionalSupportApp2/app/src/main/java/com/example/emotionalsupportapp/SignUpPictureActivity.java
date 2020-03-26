package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpPictureActivity extends AppCompatActivity {

    private Button nowCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_picture);


        nowCreated = (Button) findViewById(R.id.signInButton);
        nowCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainer();
            }
        });

    }
    public void openMainer(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
