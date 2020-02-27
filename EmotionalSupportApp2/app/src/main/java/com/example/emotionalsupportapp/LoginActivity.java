package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button signUpLink;
    private Button loggedInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        signUpLink = (Button) findViewById(R.id.signUpButton);
//        signUpLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openSignUp(v);
//            }
//        });
//        loggedInLink = (Button) findViewById(R.id.signInButton);
//        loggedInLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openMain(v);
//            }
//        });
    }

    public void openSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
    public void openMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
