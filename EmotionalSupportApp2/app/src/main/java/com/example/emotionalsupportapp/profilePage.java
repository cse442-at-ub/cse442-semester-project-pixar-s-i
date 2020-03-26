package com.example.emotionalsupportapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class profilePage extends Activity {
    private Button changeImage;
    private ImageButton LogoutBtn;
    private ImageView profilePicture;
    private Button BackBtn;
    private Button SettingsBtn;
    private RatingBar highFiverBar;
    private RatingBar motivationBar;
    private TextView emailBar;
    private TextView name;
    private  TextView emergencyContact;
    public RequestQueue reqQueue;
    public int idNum = 1;
    public String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getInfoProfile.php/?id=" + idNum;
    public String phpURLHighFive = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getHighFiveRatingProfile.php/?id=" + idNum + "&meetingType=0";
    public String phpURLMotivation = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getHighFiveRatingProfile.php/?id=" + idNum + "&meetingType=2";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepagelayout);

        //SQL Initialization Here
        String sessionId = getIntent().getStringExtra("EXTRA_USER_ID");
        //idNum = Integer.getInteger(sessionId);

        //phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getInfoProfile.php/?id=" + idNum;
        //phpURLHighFive = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getHighFiveRatingProfile.php/?id=" + idNum + "&meetingType=0";
        //phpURLMotivation = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getHighFiveRatingProfile.php/?id=" + idNum + "&meetingType=2";

        changeImage = (Button) findViewById(R.id.changeImage);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this,ChangeImage.class);
                intent.putExtra("EXTRA_USER_ID", idNum);
                startActivity(intent);
            }

        });
        LogoutBtn =  findViewById(R.id.logout);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        BackBtn =  findViewById(R.id.backButton);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this,MainActivity.class);
                intent.putExtra("EXTRA_USER_ID", idNum);
                startActivity(intent);
            }
        });
        SettingsBtn =  findViewById(R.id.SettingBtn);
        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(profilePage.this,SettingsActivity.class);
                intent.putExtra("EXTRA_USER_ID", idNum);
                startActivity(intent);
            }
        });

        highFiverBar = (RatingBar) findViewById(R.id.HighFiveRatingBar);
        motivationBar = (RatingBar) findViewById(R.id.MotivationRatingBar);
        highFiverBar.setIsIndicator(true);
        motivationBar.setIsIndicator(true);

        profilePicture = (ImageView) findViewById(R.id.imageView);
        emailBar = (TextView) findViewById(R.id.emailBar);
        name = (TextView) findViewById(R.id.textView3);

        highFiverBar.setNumStars(5);
        motivationBar.setNumStars(5);

        highFiverBar.setRating(5);
        motivationBar.setRating(5);

        emergencyContact = (TextView) findViewById(R.id.emergencyContactNumber);

        initializeSql();
    }

    /*
    Sets the Rating of toSet to rating.

    Param @rating = rating to set bar to
          @toSet = the name of the bar to set, FORMAT EXAMPLE: "highFiverBar"
     */
    public void setUserRating(int rating, String toSet){
        if(toSet.equals("highFiverBar")){
            highFiverBar.setRating(rating);
        }else if(toSet.equals("motivationBar")){
            motivationBar.setRating(rating);
        }

    }

    public void changeImageOfProfile(Icon imageToSet){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            profilePicture.setImageIcon(imageToSet);
        }
    }

    public void setEmail(String email){
        emailBar.setText(email);
    }


    /*
    Connect to database
     */
    public void initializeSql(){
        reqQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray prof = response.getJSONArray("profiles");
                    emailBar.setText(prof.getJSONObject(0).getString("eMail"));
                    emergencyContact.setText(prof.getJSONObject(0).getString("emergencyContact"));
                    name.setText(prof.getJSONObject(0).getString("FirstName") + " " + prof.getJSONObject(0).getString("LastName"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR:", "Error on Volley: " + error.toString());
            }
        });
        reqQueue.add(jsonObjectRequest);

        JsonObjectRequest jsonObjectRequestH5 = new JsonObjectRequest(Request.Method.POST, phpURLHighFive, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responses) {
                try {
                    Log.d("JSON: ", "Resp = " + responses.toString());
                    String prof = responses.getString("Ratings");

                    highFiverBar.setRating(Integer.parseInt(prof));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR:", "Error on Volley For Rating: " + error.toString());
            }
        });
        reqQueue.add(jsonObjectRequestH5);

        JsonObjectRequest jsonObjectRequestHMot = new JsonObjectRequest(Request.Method.POST, phpURLMotivation, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responses) {
                try {
                    Log.d("JSON: ", "Resp = " + responses.toString());
                    String prof = responses.getString("Ratings");

                    motivationBar.setRating(Integer.parseInt(prof));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR:", "Error on Volley For Rating: " + error.toString());
            }
        });
        reqQueue.add(jsonObjectRequestHMot);

    }
}