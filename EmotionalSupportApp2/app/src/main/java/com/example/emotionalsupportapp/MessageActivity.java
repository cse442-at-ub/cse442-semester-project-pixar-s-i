package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    TextView username;

    ImageButton btn_send;
    EditText text_send;

    MessageAdpater messageAdpater;
    ArrayList<String> chats;

    RecyclerView recyclerView;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        username = findViewById(R.id.username);
        intent =getIntent();
        final String user = intent.getStringExtra("username");
        username.setText(user);

        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        chats = new ArrayList<>();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                Log.d("CREATION", msg);
                if (!msg.equals("")){
                    sendMessage(user, msg); }
                text_send.setText("");
            }
        });

        readMessages();

    }

    private void sendMessage(String reciever, String message){
        chats.add(message);
    }

    private void readMessages(){

        messageAdpater = new MessageAdpater(chats, MessageActivity.this);
        recyclerView.setAdapter(messageAdpater);
    }
}
