package com.example.emotionalsupportapp.Highfive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.emotionalsupportapp.R;

public class HighFiveLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_location);
        if(Build.VERSION.SDK_INT >= 23){
            //Ask for permission to get location data
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //Request location
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            }
            else{
                //Ask for permission
                startLocationService();
            }

        }
        else{
            //Start Location Service if no permission needed
        }
    }

    //Method to Start location services
    void startLocationService(){
       // Intent intent = new Intent(HighFiveLocationActivity.this, LocationService.class);
        //startService(intent);

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
                    Toast.makeText(this,"Cant Find High Fiver without permission",Toast.LENGTH_LONG).show();
                }
        };
    }
}