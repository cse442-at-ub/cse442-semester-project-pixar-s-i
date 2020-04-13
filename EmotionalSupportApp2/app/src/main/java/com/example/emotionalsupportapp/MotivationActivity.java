package com.example.emotionalsupportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class MotivationActivity extends Activity {
    private String userID;
    private String userName;

    public interface VolleyCallback {
        void onSuccessResponse(String username);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motivationlayout);

//        String userID = getIntent().getStringExtra("EXTRA_USER_ID");
        userID = "1";

        getUsername((new VolleyCallback() {
            @Override
            public void onSuccessResponse(String username) {
                userName = username;
            }
        }));
    }

    public void goToSearchPage(View view) {
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        intent.putExtra("EXTRA_USER_NAME", userName);
        startActivity(intent);
    }

    public void goToAcceptPage(View view) {
        Intent intent = new Intent(this,AcceptActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        intent.putExtra("EXTRA_USER_NAME", userName);
        startActivity(intent);
    }

    private void getUsername(final VolleyCallback callback){
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getUsername.php/?user_id=" + userID;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject re = response.getJSONObject("response");
                    String username = re.getString("FirstName") + " " + re.getString("LastName");
                    callback.onSuccessResponse(username);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteVolunteerMessages();
    }

    private void deleteVolunteerMessages() {
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/deleteMessages.php/?user_id=" + userID;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR:", "Error on Volley: " + error.toString());
            }
        });
        reqQueue.add(jsonObjectRequest);
    }
}