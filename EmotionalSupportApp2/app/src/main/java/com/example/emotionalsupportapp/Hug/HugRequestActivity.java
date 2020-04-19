package com.example.emotionalsupportapp.Hug;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.example.emotionalsupportapp.Highfive.HighFiveRequestActivity;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.Member.Registration.LoginActivity;
import com.example.emotionalsupportapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class HugRequestActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    private String userID;
    private String volunteerID;
    private static final int REQUEST_CODE = 101;
    private boolean userFound;

    private Location lastLocation;
    private Dialog progressDialog;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Handler handler;
    private GoogleMap mMap;
    private MarkerOptions currentUserLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug_request);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        userFound = false;
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            userID = b.getString("EXTRA_USER_ID");
        }else{
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
        currentUserLocationMarker = new MarkerOptions();
        handler = new Handler();
        progressDialog = new ProgressDialog(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationCallback = new UpdateLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_high_five);
        mapFragment.getMapAsync(this);

    }

    private  class UpdateLocation extends LocationCallback{
        @Override
        public void onLocationResult(LocationResult locationResult) {
            lastLocation = locationResult.getLastLocation();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        startLocationUpdates();
        updateLocationUI();


    }
    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    public void updateLocationUI(){
        if (mMap == null) {
            return;
        }
        try {
            if(lastLocation == null){
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null) {
                                    lastLocation = location;
                                    mMap.setMyLocationEnabled(true);
                                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()),12));
                                    currentUserLocationMarker.position(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())).title("You are Here");
                                    mMap.addMarker(currentUserLocationMarker);

                                }else{
                                    startLocationUpdates();
                                }
                            }
                        });
            }else{
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()),12));
                currentUserLocationMarker.position(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())).title("You are Here");
                mMap.addMarker(currentUserLocationMarker);
            }


        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {

    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    protected void onDestroy() {
        stopLocationUpdates();
        super.onDestroy();
    }
}