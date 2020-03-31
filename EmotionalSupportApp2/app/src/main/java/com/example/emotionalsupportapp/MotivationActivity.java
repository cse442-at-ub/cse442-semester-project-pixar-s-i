package com.example.emotionalsupportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MotivationActivity extends Activity {
    int userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motivationlayout);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = 1;
    }

    public void goToSearchPage(View view) {
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }

    public void goToAcceptPage(View view) {
        Intent intent = new Intent(this,AcceptActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
}