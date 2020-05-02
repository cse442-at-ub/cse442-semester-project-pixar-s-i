package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.Connect.ConnectActivity;
import com.example.emotionalsupportapp.Member.Registration.LoginActivity;
import com.example.emotionalsupportapp.Motivation.MotivationActivity;
import com.example.emotionalsupportapp.Talk.TalkActivity;
import com.example.emotionalsupportapp.Highfive.HighFiveActivity;
import com.example.emotionalsupportapp.Hug.HugActivity;
import com.example.emotionalsupportapp.Member.Profile.profilePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String userID;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            userID = getIntent().getExtras().getString("EXTRA_USER_ID");
            getUserName(userID);
        }
        else{
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
    }
    private void getUserName(final String userId){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringBuffer fullURL = new StringBuffer();
        fullURL.append(getString(R.string.database_url));
        fullURL.append("getUserData.php");

        StringRequest getUsername = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray userdata = new JSONArray(response);
                    Log.e("Username ",userdata.getString(0) + "");
                    userName = userdata.getString(0);
                } catch (JSONException e) {
                    Log.e("Username Json Error",e + "");
                }

                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Username Error",error + "");
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userID", userId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(getUsername);

    }

    /*
     * Changes to the main high five page
     * should be used to change to the high five page
     * Called when high five button is clicked
     */
    public void goToHighFivePage(View view){
        Intent intent = new Intent(this, HighFiveActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        intent.putExtra("EXTRA_USERNAME",userName);
        startActivity(intent);
    }
    /*
     * Changes to the main hug page
     * should be used to change to the hug page
     * Called when hug button is clicked
     */
    public void goToHugPage(View view){
        Intent intent = new Intent(this, HugActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        intent.putExtra("EXTRA_USERNAME",userName);

        startActivity(intent);
    }
    /*
     * Changes to the main Motivation page
     * should be used to change to the Motivation page
     * Called when Motivation button is clicked
     */
    public void goToMotivationPage(View view){
        Intent intent = new Intent(this, MotivationActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Talk page
     * should be used to change to the Talk page
     * Called when talk button is clicked
     */
    public void goToTalkPage(View view){
        Intent intent = new Intent(this, TalkActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Connect page
     * should be used to change to the Connect page
     * Called when Connect button is clicked
     */
    public void goToConnectPage(View view){
        Intent intent = new Intent(this, ConnectActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Profile page
     * should be used to change to the Profile page
     * Called when Profile button is clicked
     */
    public void goToProfilePage(View view){
        Intent intent = new Intent(this, profilePage.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
}
