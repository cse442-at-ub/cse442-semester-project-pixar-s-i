package com.example.emotionalsupportapp.Member.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class ComplaintsActivity extends Activity {
    private Button submit;
    private EditText ComplaintBar;
    public RequestQueue reqQueue;

    public String idNum = "1";

    public String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/postComplaint.php/?userID=" + idNum + "&complaint=" + "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        reqQueue = Volley.newRequestQueue(getApplicationContext());

        idNum = getIntent().getStringExtra("EXTRA_USER_ID");
        ComplaintBar = findViewById(R.id.ComplaintBar);


        submit =  findViewById(R.id.button5);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String temp = ComplaintBar.getText().toString();
               // String tem = temp.toString();
                //Log.e("Response",tem + "");
                phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/postComplaint.php/?userID=" + idNum + "&complaint=" + temp;
                JsonObjectRequest php = new JsonObjectRequest(Request.Method.GET, phpURLBase, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String resp = response.getString("response");
                            displayToast(resp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley update Error",error + "");

                    }
                });
                reqQueue.add(php);
            }
        });
    }

    public void displayToast(String message){
        if(message.equals("Y")) {
            Toast.makeText(getApplicationContext(), "Thanks For Your Input!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Something Went Wrong...", Toast.LENGTH_SHORT).show();
        }
    }
}
