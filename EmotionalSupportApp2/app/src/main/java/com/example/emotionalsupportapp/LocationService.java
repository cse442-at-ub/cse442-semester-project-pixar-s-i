package com.example.emotionalsupportapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service {

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback(){
            @Override
            //Location Change method
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d("myLog","Lat is: " + locationResult.getLastLocation().getLatitude() + ", "
                        + "Long is: " + locationResult.getLastLocation().getLongitude());

                //BroadCast(Pass) Location to be used in High Five and Hug Activity
                Intent intent = new Intent("ACT_LOC");
                intent.putExtra("latitude",locationResult.getLastLocation().getLatitude());
                intent.putExtra("longitude",locationResult.getLastLocation().getLongitude());
                sendBroadcast(intent);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    //Requesting Location method
    private void requestLocation(){
        LocationRequest locationRequest = new LocationRequest();
        //Interval for location request
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
    }
}
