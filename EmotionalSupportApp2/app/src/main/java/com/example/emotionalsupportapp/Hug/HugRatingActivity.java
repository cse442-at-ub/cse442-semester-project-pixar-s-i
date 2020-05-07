package com.example.emotionalsupportapp.Hug;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.Member.Registration.LoginActivity;
import com.example.emotionalsupportapp.R;

import java.util.HashMap;
import java.util.Map;

public class HugRatingActivity extends AppCompatActivity {

    private String userID;
    private String volunteerID;
    private RatingBar ratingBar;
    private Button submit;
    private String rating;
    private  String volunteerName;
    private String userName;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug_rating);
        ratingBar = (RatingBar) findViewById(R.id.hug_rating_bar);
        submit = (Button) findViewById(R.id.hug_submit_button);
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            userID = b.getString("EXTRA_USER_ID");
            volunteerID = b.getString("EXTRA_VOLUNTEER_ID");
            userName= b.getString("EXTRA_USER_NAME");
            volunteerName = b.getString("EXTRA_VOLUNTEER_NAME");
            textView = (TextView) findViewById(R.id.textView7);

            textView.setText("How was your High Five with " +volunteerName+ ".");

        }else{
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
    }

    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        rating = Float.toString(ratingBar.getRating());
        updateRating(volunteerID, rating);
        returnToMainIntent.putExtra("EXTRA_USER_ID",userID);
        startActivity(returnToMainIntent);
    }

    @Override
    public void onBackPressed() {
        Intent goToHug = new Intent(this, HugActivity.class);
        goToHug.putExtra("EXTRA_USER_ID",userID);
        startActivity(goToHug);
    }

    private void updateRating(final String volunteerID, final String rating){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting rating. Please wait.");
        progressDialog.show();
        String phpfile = getString(R.string.update_hug_rating);
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
