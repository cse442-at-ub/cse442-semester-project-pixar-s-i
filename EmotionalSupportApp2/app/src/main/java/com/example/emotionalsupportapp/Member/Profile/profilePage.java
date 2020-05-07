package com.example.emotionalsupportapp.Member.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;
import com.example.emotionalsupportapp.Settings.SettingsActivity;
import com.example.emotionalsupportapp.Member.Registration.LoginActivity;
import com.example.emotionalsupportapp.Member.Profile.ComplaintsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profilePage extends Activity {

    private Button report;
    private ImageButton LogoutBtn;
    private ImageView profilePicture;
    private Button BackBtn;
    private Button SettingsBtn;
    private RatingBar highFiverBar;
    private RatingBar hugBar;
    private TextView emailBar;
    private TextView name;
    private  TextView emergencyContact;
    public RequestQueue reqQueue;
    public String idNum;
    public String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getInfoProfile.php/?id=" + idNum;
    public String phpURLHighFive = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getHighFiveRatingProfile.php/?id=" + idNum + "&meetingType=0";
    public String phpURLMotivation = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getMotivationRatingProfile.php/?id=" + idNum + "&meetingType=2";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepagelayout);

        //SQL Initialization Here
        idNum = getIntent().getStringExtra("EXTRA_USER_ID");

        phpURLHighFive = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getHighFiveRatingProfile.php/?id=" + idNum;
        phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getInfoProfile.php/?id=" + idNum+ "&meetingType=0";
        phpURLMotivation = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getHighFiveRatingProfile.php/?id=" + idNum + "&meetingType=2";

        report =  findViewById(R.id.reportButton);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this, ComplaintsActivity.class);
                intent.putExtra("EXTRA_USER_ID", idNum);
                startActivity(intent);
            }
        });
        LogoutBtn =  findViewById(R.id.logout);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SharedPreferences sp = getSharedPreferences("Login",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putBoolean("Login",false);
                ed.commit();
                Intent intent = new Intent(profilePage.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        BackBtn =  findViewById(R.id.backButton);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this, MainActivity.class);
                intent.putExtra("EXTRA_USER_ID", idNum);
                startActivity(intent);
            }
        });
        SettingsBtn =  findViewById(R.id.SettingBtn);
        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this, SettingsActivity.class);
                intent.putExtra("EXTRA_USER_ID", idNum);
                startActivity(intent);
            }
        });

        highFiverBar = (RatingBar) findViewById(R.id.HighFiveRatingBar);
        hugBar = (RatingBar) findViewById(R.id.HugRatingBar);
        highFiverBar.setIsIndicator(true);
        hugBar.setIsIndicator(true);

        profilePicture = (ImageView) findViewById(R.id.imageView);
        emailBar = (TextView) findViewById(R.id.emailBar);
        name = (TextView) findViewById(R.id.username);
        emergencyContact = (TextView) findViewById(R.id.emergencyContactNumber);

        getRatings();
        highFiverBar.setNumStars(5);
        hugBar.setNumStars(5);


    }
    public void getRatings(){
        StringBuffer fullURL = new StringBuffer();
        fullURL.append(getString(R.string.database_url));
        fullURL.append("retrieveHighFiveRating.php");

        StringRequest highFiverating = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray userdata = new JSONArray(response);
                    Log.e("High Fives",userdata + "");

                    highFiverBar.setRating(Float.valueOf(userdata.getString(0)));

                } catch (JSONException e) {
                    Log.e("High Json Error",e + "");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Username Error",error + "");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userID", idNum);
                return params;
            }
        };
        fullURL = new StringBuffer();
        fullURL.append(getString(R.string.database_url));
        fullURL.append("retrieveHugRating.php");

        StringRequest hugRating = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray userdata = new JSONArray(response);
                    Log.e("Hugs",userdata + "");
                    hugBar.setRating(Float.valueOf(userdata.getString(0)));

                } catch (JSONException e) {
                    Log.e("Hug Json Error",e + "");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Username Error",error + "");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userID", idNum);
                return params;
            }
        };
        fullURL = new StringBuffer();
        fullURL.append(getString(R.string.database_url));
        fullURL.append("getEmergencyContact.php");
        StringRequest getContact = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray userdata = new JSONArray(response);
                    Log.e("Contact ",userdata + "");
                    emergencyContact.setText(userdata.getString(0));

                } catch (JSONException e) {
                    Log.e("Contact Json Error",e + "");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Contact Error",error + "");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userID", idNum);
                return params;
            }
        };
        fullURL = new StringBuffer();
        fullURL.append(getString(R.string.database_url));
        fullURL.append("getEmail.php");
        StringRequest getEmail = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray userdata = new JSONArray(response);
                    Log.e("Email",userdata + "");
                    emailBar.setText(userdata.getString(0));

                } catch (JSONException e) {
                    Log.e("Email Json Error",e + "");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Email Error",error + "");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", idNum);
                return params;
            }
        };
        fullURL = new StringBuffer();
        fullURL.append(getString(R.string.database_url));
        fullURL.append("getUserData.php");
        StringRequest getUsername = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray userdata = new JSONArray(response);
                    Log.e("Username",userdata + "");
                    name.setText(userdata.getString(0));

                } catch (JSONException e) {
                    Log.e("Email Json Error",e + "");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Email Error",error + "");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userID", idNum);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(getUsername);
        requestQueue.add(getEmail);
        requestQueue.add(getContact);
        requestQueue.add(highFiverating);
        requestQueue.add(hugRating);
    }

}
