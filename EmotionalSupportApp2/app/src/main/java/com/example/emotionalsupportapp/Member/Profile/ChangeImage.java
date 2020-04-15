package com.example.emotionalsupportapp.Member.Profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.emotionalsupportapp.R;

public class ChangeImage extends Activity {
    public Button button;
    int userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeimagelayout);

        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = 1;

        button = (Button) findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ChangeImage.this, profilePage.class);
                intent.putExtra("EXTRA_USER_ID", userID);
                startActivity(intent);
            }

        });

    }
}