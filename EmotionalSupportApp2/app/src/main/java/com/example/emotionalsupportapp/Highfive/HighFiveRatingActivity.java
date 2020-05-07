package com.example.emotionalsupportapp.Highfive;

//import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.Hug.HugActivity;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.Member.Registration.LoginActivity;
import com.example.emotionalsupportapp.R;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class HighFiveRatingActivity extends AppCompatActivity {

    private String userID;
    private String userName;
    private String volunteerID;
    private String voluneerName;
    private Button submitButton;
    private String rating;
    private RatingBar ratingBar;
    private  String volunteerName;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_rating);
        ratingBar = (RatingBar) findViewById(R.id.high_five_rating_bar);
        submitButton = (Button) findViewById(R.id.submit_button);
        textView = (TextView) findViewById(R.id.volunteer_interaction_text);
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            userID = b.getString("EXTRA_USER_ID");
            volunteerID = b.getString("EXTRA_VOLUNTEER_ID");
            userName= b.getString("EXTRA_USER_NAME");
            volunteerName = b.getString("EXTRA_VOLUNTEER_NAME");

        }

            textView.setText("How was your High Five with " +volunteerName+ ".");


    }

    @Override
    public void onBackPressed() {
        Intent goToHighFive = new Intent(this, HighFiveActivity.class);
        goToHighFive.putExtra("EXTRA_USER_ID",userID);
        startActivity(goToHighFive);
    }
    //Updating the user rating and return to the main page
    public void updateRatingandReturnToMain(View view){
        rating = Float.toString(ratingBar.getRating());
        updateRating(volunteerID, rating);
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        returnToMainIntent.putExtra("EXTRA_USER_ID",userID);
        startActivity(returnToMainIntent);
    }

    private void updateRating(final String volunteerID, final String rating){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting rating. Please wait.");
        progressDialog.show();
        String phpfile = "updateHighFiveRating.php";
        StringBuilder fullURL = new StringBuilder();
        fullURL.append(getString((R.string.database_url)));
        fullURL.append(phpfile);
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                progressDialog.dismiss();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response Error", error + " ");
                progressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                HashMap<String,String> query = new HashMap<>();
                query.put("userID",volunteerID);
                query.put("rating",rating);
                return query;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }



}
