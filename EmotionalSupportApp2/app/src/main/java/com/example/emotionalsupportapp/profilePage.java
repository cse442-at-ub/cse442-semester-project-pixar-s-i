package com.example.emotionalsupportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class profilePage extends Activity {
    private Button changeImage;
    private ImageButton LogoutBtn;
    private Button BackBtn;
    private Button SettingsBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepagelayout);

        changeImage = (Button) findViewById(R.id.changeImage);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this,ChangeImage.class);
                startActivity(intent);
            }

        });
        LogoutBtn =  findViewById(R.id.logout);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        BackBtn =  findViewById(R.id.backButton);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this,MainActivity.class);
                startActivity(intent);
            }
        });
        SettingsBtn =  findViewById(R.id.backButton);
        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}