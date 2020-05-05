package com.example.emotionalsupportapp.Motivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.R;
import com.example.emotionalsupportapp.Volunteer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends AppCompatActivity {
    private String userId;
    private String userName;

    private RecyclerView recyclerView;
    private TopFiveVolunteerAdapter topFiveVolunteerAdapter;
    private ArrayList<Volunteer> topFiveVolunteers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        userId = getIntent().getStringExtra("EXTRA_USER_ID");
        userName = getIntent().getStringExtra("EXTRA_USER_NAME");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        topFiveVolunteers = new ArrayList<>();
        getTopFiveVolunteers();
    }

    private void getTopFiveVolunteers() {
        topFiveVolunteers.clear();
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getAllRatings.php";
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            try {
                JSONArray re = response.getJSONArray("response");
                for (int i = 0; i < re.length(); i++) {
                    JSONObject jsonobject = re.getJSONObject(i);
                    String volunteerId = jsonobject.getString("userId");
                    String firstName = jsonobject.getString("FirstName");
                    String lastName = jsonobject.getString("LastName");
                    String volunteerName = firstName + " " + lastName;
                    String highFiveRating = jsonobject.getString("VolunteerHighFive");
                    String hugRating = jsonobject.getString("VolunteerHug");
                    String motivateRating = jsonobject.getString("VolunteerMotivate");
                    String overallRating = Integer.toString((Integer.parseInt(highFiveRating) + Integer.parseInt(hugRating) + Integer.parseInt(motivateRating)));
                    topFiveVolunteers.add(new Volunteer(userId, userName, volunteerId,volunteerName,overallRating));
                }
                Collections.sort(topFiveVolunteers);
                topFiveVolunteerAdapter = new TopFiveVolunteerAdapter(new ArrayList<>(topFiveVolunteers.subList(0, Math.min(topFiveVolunteers.size(), 5))), SearchActivity.this);
                recyclerView.setAdapter(topFiveVolunteerAdapter);
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

    public void refreshVolunteers(View view) {
        getTopFiveVolunteers();
        Log.d("TEST", "11111111111111111111");
    }

}
