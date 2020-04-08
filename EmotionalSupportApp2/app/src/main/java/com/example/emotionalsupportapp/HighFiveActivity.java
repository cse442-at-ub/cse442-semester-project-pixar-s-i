package com.example.emotionalsupportapp;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import static com.example.emotionalsupportapp.EmotionalSupport.CHANNEL_1_ID;

public class HighFiveActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private FusedLocationProviderClient locationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        notificationManager = NotificationManagerCompat.from(this);
    }

    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);

    }

    //Check if location is turned on before attempting to search for location
    public void beginHighFiveSearch(View view){
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try{
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(!gps_enabled && !network_enabled){
            new AlertDialog.Builder(HighFiveActivity.this)
                    .setMessage("GPS Enable")
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .show();
        }else{
            Intent highFiveSearch = new Intent(this, HighFiveRequestActivity.class);
            startActivity(highFiveSearch);
        }

    }


}