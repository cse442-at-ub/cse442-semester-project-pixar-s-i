package com.example.emotionalsupportapp;

<<<<<<< HEAD
import android.content.Intent;
||||||| aa225b8... Adding Location Services/ Fixing Bugs #97
import android.Manifest;

import android.content.pm.PackageManager;
import android.location.Location;
=======
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
>>>>>>> parent of aa225b8... Adding Location Services/ Fixing Bugs #97
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
||||||| aa225b8... Adding Location Services/ Fixing Bugs #97
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
=======
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
>>>>>>> parent of aa225b8... Adding Location Services/ Fixing Bugs #97

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;
||||||| aa225b8... Adding Location Services/ Fixing Bugs #97
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
=======
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
>>>>>>> parent of aa225b8... Adding Location Services/ Fixing Bugs #97

<<<<<<< HEAD
public class HighFiveRequestActivity extends AppCompatActivity {
||||||| aa225b8... Adding Location Services/ Fixing Bugs #97
import org.json.JSONObject;

public class HighFiveRequestActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    RequestQueue reqQueue;
    private final String phpurl = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/";
    private static final int REQUEST_CODE = 101;
    int id;
    Button search;
=======
public class HighFiveRequestActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
>>>>>>> parent of aa225b8... Adding Location Services/ Fixing Bugs #97

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_request);
<<<<<<< HEAD
||||||| aa225b8... Adding Location Services/ Fixing Bugs #97
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        search  = findViewById(R.id.request_high_five);
        startLocationService();
    }

    //Method to Start location services
    void startLocationService() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(HighFiveRequestActivity.this);

                }
            }
        });
=======
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationService();

    }

    //Method to Start location services
    void startLocationService() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(HighFiveRequestActivity.this);

                }
            }
        });
>>>>>>> parent of aa225b8... Adding Location Services/ Fixing Bugs #97
    }
<<<<<<< HEAD
    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
||||||| aa225b8... Adding Location Services/ Fixing Bugs #97

    //    Set Up the Google Maps Location
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("You are Here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        googleMap.addMarker(markerOptions);

=======


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("You are Here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));
        googleMap.addMarker(markerOptions);
>>>>>>> parent of aa225b8... Adding Location Services/ Fixing Bugs #97
    }
<<<<<<< HEAD
}
||||||| aa225b8... Adding Location Services/ Fixing Bugs #97

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocationService();
                }
                break;
        }
    }

    public void sendHFNotification(View v){
        reqQueue = Volley.newRequestQueue(getApplicationContext());

        String query = "connectToFireBase.php?id=" + 1 + "&topic=/topics/highFiver" + "&lat=" + currentLocation.getLatitude() + "&lon=" + currentLocation.getLongitude()
                + "&title=High Five" + "&message=User Request High Five";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpurl + query, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        search.setText("Locating High Fiver..");
        reqQueue.add(jsonObjectRequest);
    }

}


=======

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocationService();
                }
                break;
        }
    }
}
>>>>>>> parent of aa225b8... Adding Location Services/ Fixing Bugs #97
