package com.example.emotionalsupportapp.Highfive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;

public class HighFiveVolunteerActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_volunteer);
    }

    @Override
    protected void onStart() {
        //Called when restarted activity
        String[] foods = {"Bacon","Candy","Meatball","Potato"};
        ListAdapter highFiveAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,foods);
        ListView highfiveListView = (ListView) findViewById(R.id.high_five_requester);
        highfiveListView.setAdapter(highFiveAdapter);
        super.onStart();
    }

    public void returnHome(View v){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }
    public void confirmRequest(View v){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }

}