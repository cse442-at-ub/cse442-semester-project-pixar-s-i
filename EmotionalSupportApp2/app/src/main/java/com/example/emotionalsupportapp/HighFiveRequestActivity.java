package com.example.emotionalsupportapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HighFiveRequestActivity extends AppCompatActivity {
        private TextView lat;
        private TextView lon;
        private double latit;
        private double longit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_request);
        lon = findViewById(R.id.lon);
        lat = findViewById(R.id.lat);
        if (Build.VERSION.SDK_INT >= 23) {
            //Ask for permission to get location data
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Request location
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                //Ask for permission
                startLocationService();
            }

        } else {
            //Start Location Service if no permission needed
        }
    }
    //Method to Start location services
    void startLocationService(){
        LocationBroadcastReceiver receiver = new LocationBroadcastReceiver();
        IntentFilter filter = new IntentFilter("ACT_LOC");
        registerReceiver(receiver,filter);
        Intent intent = new Intent(HighFiveRequestActivity.this,LocationService.class);
        startService(intent);

    }
    //Check if permission was granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocationService();
                }
                else{
//                    Toast.makeText(this,"Cant Find High Fiver without permission",Toast.LENGTH_LONG).show();
                }
        };
    }
        public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }

    /*
        Gets the location data from the location services
        This is where the location will be mapped to a specific location on campus
     */
    public class LocationBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //Check if action is the one in Location Services

            if(intent.getAction().equals("ACT_LOC")){
                double latitude = intent.getDoubleExtra("latitude",0f);
                double longitude = intent.getDoubleExtra("longitude",0f);
                latit  =  latitude;
                longit = longitude;
                lat.setText("Latitude: "+ latitude);
                lon.setText("Longitude: " +longitude);
            }
        }
    }


    public void volunteerAndLocationInfo (View view){
        Intent volunteerAndLocationInfo_ = new Intent(this, VolunteerAndLocationInfoActivity.class);
        volunteerAndLocationInfo_.putExtra("lat", latit);
        volunteerAndLocationInfo_.putExtra("lon",longit);
        startActivity(volunteerAndLocationInfo_);
    }

}
