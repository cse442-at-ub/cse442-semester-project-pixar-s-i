package com.example.emotionalsupportapp.Hug;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HugActivity extends AppCompatActivity {

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug);

        userID = getIntent().getStringExtra("EXTRA_USER_ID");
  

    }

    public void returnToMain(View view){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }

    public void beginHugSearch(View view){
        Intent HugRequest = new Intent(this,HugRequestActivity.class);
        HugRequest.putExtra("EXTRA_USER_ID",userID);
        sendFCMPush();
        startActivity(HugRequest);
    }

    //Sends a notification request to the firebase messaging service specific to people subscribed to the high five topic
    private void sendFCMPush() {
        FirebaseMessaging.getInstance().subscribeToTopic("Hug");
        String msg = "A User Request Hug";
        String title = "Hug Request";
        String token = "/topics/Hug";
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
}
