package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.example.emotionalsupportapp.Highfive.HighFiveRatingActivity;

public class VolunteerAndLocationInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_and_location_info);
        Intent info = getIntent();
        double lat =(info.getDoubleExtra("lat",0.00 ));
        double lon = (info.getDoubleExtra("lon",0.00));
        Pair<Double,Double> user = new Pair<>(lat,lon);
        VolunteerSelector inst = new VolunteerSelector();
        LocationSelector inst2 = new LocationSelector();
        inst2.createListOfLocations();
        inst.createListOfVolunteers();
        double minDistance = Double.MAX_VALUE;
        Pair<String, Pair<Double,Double>> chosenVolunteer = new Pair<>("Dummy", new Pair<Double, Double>(0.00,0.00));
        for(int x = 0; x < inst.listOfVolunteers.size(); x++){
            double currentDistance = inst.findDistance(user, inst.listOfVolunteers.get(x));
            if(currentDistance < minDistance){
                minDistance = currentDistance;
                chosenVolunteer = inst.listOfVolunteers.get(x);
            }
        }
        Pair<Double,Double> midPoint = inst2.findMidPoint(user,chosenVolunteer);
        Pair<String, Pair<Double,Double>> meetUpLocation = inst2.meetUpLocationFound(midPoint);
        TextView output = findViewById(R.id.volunteerAndLocationInfo_);
        output.setText("Meet " + chosenVolunteer.first + " at " + meetUpLocation.first + ".");



    }

    public void rateVolunteer(View view){
        Intent rate = new Intent(this, HighFiveRatingActivity.class);
        startActivity(rate);
    }

    public void apology(View view){
        Intent missedMeetUp = new Intent(this, MissedMeetUpActivity.class);
        startActivity(missedMeetUp);
    }


}
