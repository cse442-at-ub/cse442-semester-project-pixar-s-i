package com.example.emotionalsupportapp.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.emotionalsupportapp.R;

public class MessageNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notification);

        TextView newMessage = findViewById(R.id.new_message);

        String message = getIntent().getStringExtra("message");
        newMessage.setText(message);

        Log.d("Test", message);
    }
}
