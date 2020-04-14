package com.example.emotionalsupportapp.Highfive;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;
import com.example.emotionalsupportapp.Service.MyFirebaseMessagingService;
import com.example.emotionalsupportapp.Service.RequestsListAdapter;
import com.example.emotionalsupportapp.Service.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighFiveActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private NotificationManagerCompat notificationManager;
    private FusedLocationProviderClient locationProviderClient;
    private String FCM_TOKEN;
    private JSONArray users;
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<User> userList;
    private RecyclerView.Adapter adapter;

    // Location Information
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    Intent highFiveSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        notificationManager = NotificationManagerCompat.from(this);
        highFiveSearch = new Intent(this, HighFiveRequestActivity.class);
        highFiveSearch.putExtra("userFound",false);
        mList = findViewById(R.id.high_five_request_list);
        userList = new ArrayList<>();
        adapter = new RequestsListAdapter(getApplicationContext(), userList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationCallBack = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult == null){
                    return;
                }
                else{
                    for(Location location: locationResult.getLocations()){
                        Log.e("Location Data",location.toString());
                        super.onLocationResult(locationResult);
                    }
                }
            }
        };
    }

    @Override
    protected void onStart() {
        String query = "";
        String phpfile = "allActiveUsers.php";
        userList.clear();
        getRequestsData(phpfile, query);

        super.onStart();
    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }

    public void returnToMain(View view) {
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);

    }

    //Check if location is turned on before attempting to search for location
    public void beginHighFiveSearch(View view) {
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
            new AlertDialog.Builder(HighFiveActivity.this)
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
            sendActiveUserPush();
            sendFCMPush();
            stopLocationUpdates();
            startActivity(highFiveSearch);
        }

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper());
    }

    //Make a query to the database with the userid, latitude, and longitude
    private void sendActiveUserPush() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);

        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            lastLocation = location;
                            Log.e("Location",lastLocation.toString());

                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w("Task", "getInstanceId failed", task.getException());
                                                return;
                                            }

                                            // Get new Instance ID token
                                            FCM_TOKEN = task.getResult().getToken();
                                            String phpfile = "writeCoord.php";
                                            HashMap<String,String> query = new HashMap<>();
                                            query.put("userID","1");
                                            query.put("xCord",String.valueOf(lastLocation.getLatitude()));
                                            query.put("yCord",String.valueOf(lastLocation.getLongitude()));
                                            Log.e("Query",query.toString());
                                            sendRequestToSever(phpfile,query);

                                        }
                                    });
                        }else{
                            startLocationUpdates();
                        }
                    }
                });


    }

    private void sendFCMPush() {
        FirebaseMessaging.getInstance().subscribeToTopic("High_Five");
        String msg = "User Request High Five";
        String title = "High Five Request";
        String token = "/topics/High_Five";
        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + getString(R.string.firebase_server_key));
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);

    }

    public void getRequestsData(String phpfile,String query){
        final ProgressDialog progressDialog = new ProgressDialog(this);
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
                    for(int i = 0; i < response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String username = jsonObject.getString("userID");
                        String lat = jsonObject.getString("xCord");
                             String lon = jsonObject.getString("yCord");
                        User user = new User(username,lat,lon);
                        userList.add(user);
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


    public void sendRequestToSever(String phpfile, final HashMap<String,String> query){
        StringBuilder fullURL = new StringBuilder();
        fullURL.append(getString(R.string.database_url));
        fullURL.append(phpfile);


        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response",response + " ");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error",error + "");

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return query;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

}