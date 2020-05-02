package com.example.emotionalsupportapp.Hug;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.Member.Profile.User;
import com.example.emotionalsupportapp.Member.Registration.LoginActivity;
import com.example.emotionalsupportapp.R;
import com.example.emotionalsupportapp.Service.RequestsListAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HugActivity extends AppCompatActivity {

    private RecyclerView hugList;
    private RecyclerView.Adapter adapter;
    private String userID;
    private String username;
    Intent hugRequest;
    private List<User> users;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location lastLocation;
    private ProgressDialog progressDialog;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug);

        hugRequest = new Intent(this,HugRequestActivity.class);
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            userID = b.getString("EXTRA_USER_ID");
            username = b.getString("EXTRA_USERNAME");

        }else{
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }

        hugRequest.putExtra("EXTRA_USER_ID",userID);
        progressDialog = new ProgressDialog(this);

        hugList = findViewById(R.id.hug_request_list);
        users = new ArrayList<>();

        //Set Up for List of users
        adapter = new RequestsListAdapter(getApplicationContext(), users);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(hugList.getContext(), linearLayoutManager.getOrientation());

        hugList.setHasFixedSize(true);
        hugList.setLayoutManager(linearLayoutManager);
        hugList.addItemDecoration(dividerItemDecoration);
        hugList.setAdapter(adapter);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult != null){
                    lastLocation = locationResult.getLastLocation();
                    sendLocation(lastLocation);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        String query = "";
        String phpfile = getString(R.string.hug_list);
        users.clear();
        getRequestsData(phpfile, query);

        super.onStart();
    }

    @Override
    public void onBackPressed() {
        returnToMain();
    }

    public void returnToMain(){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        returnToMainIntent.putExtra("EXTRA_USER_ID",userID);
        startActivity(returnToMainIntent);
    }

    // Creates a query with the location information for this user and calls Send request to the server
    private void sendLocation(Location location){

        lastLocation = location;
        Log.e("Location",lastLocation.toString());
        String phpfile = getString(R.string.hug_match_table);
        HashMap<String,String> query = new HashMap<>();
        query.put("userID",userID);
        query.put("xCord",String.valueOf(lastLocation.getLatitude()));
        query.put("yCord",String.valueOf(lastLocation.getLongitude()));
        Log.e("Query",query.toString());
        progressDialog.dismiss();
        sendRequestToSever(phpfile,query);

    }

    //Sends a Post request to the server using the specific query and phpfile
    public void sendRequestToSever(String phpfile, final HashMap<String,String> query){
        StringBuilder fullURL = new StringBuilder();
        fullURL.append(getString(R.string.database_url));
        fullURL.append(phpfile);

        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error",error + "");

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                return query;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }



    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }

    public void beginHugSearch(View view){
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(HugActivity.this)
                    .setMessage("GPS Enable")
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        } else {
            if(sendActiveUserPush()){
                sendFCMPush();
                stopLocationUpdates();
                startActivity(hugRequest);
            }
        }
    }
    //Make a query to the database with the userid, latitude, and longitude
    private boolean sendActiveUserPush() {
        progressDialog.setMessage("Sending Request...");
        progressDialog.show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return false;

        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            sendLocation(location);
                        }else{
                            startLocationUpdates();
                        }
                    }
                });

        return true;

    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    //Get a location update
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }


    //Sends a notification request to the firebase messaging service specific to people subscribed to the high five topic
    private void sendFCMPush() {
        FirebaseMessaging.getInstance().subscribeToTopic("Hug");
        String msg = username +" requested a Hug";
        String title = "Hug Request";
        String token = "/topics/Hug";
        JSONObject obj = null;
        JSONObject objData;
        JSONObject dataobjData;

        try {
            obj = new JSONObject();
            objData = new JSONObject();

            objData.put("body", msg);
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name
            objData.put("tag", token);
            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            obj.put("to", token);

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("return here", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.firebase_url), obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("True", response + "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("False", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "key=" + getString(R.string.firebase_server_key));
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);

    }
    //Get the list of people who request high fives from the database
    public void getRequestsData(String phpfile,String query){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringBuffer fullURL = new StringBuffer();
        fullURL.append(getString(R.string.database_url));
        fullURL.append(phpfile);
        fullURL.append(query);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(fullURL.toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    Log.e("Users List",response + "");

                    for(int i = 0; i < response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String username = jsonObject.getString("userID");
                        String lat = jsonObject.getString("xCord");
                        String lon = jsonObject.getString("yCord");
                        String first_name = jsonObject.getString("FirstName");
                        String last_name = jsonObject.getString("LastName");
                        User user = new User(username,lat,lon,first_name,last_name);
                        users.add(user);
                    }
                }catch(JSONException e){
                    Log.e("JSON Error",e.toString());
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error",error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}
