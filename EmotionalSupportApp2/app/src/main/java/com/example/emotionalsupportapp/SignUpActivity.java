package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    EditText emailText, passwordText, passwordConText, fNameText, lNameText;
    Switch hugSwitch, hFiveSwitch, motivationSwitch;
    Button nowCreated;
    String result;
    RequestQueue requestQueue;
    String signUpURL = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        nowCreated = (Button) findViewById(R.id.signInButton);
        nowCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainer();
            }
        });

    }
    public void openMainer(){

        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        passwordConText = (EditText) findViewById(R.id.passConText);
        fNameText = (EditText) findViewById(R.id.fNameText);
        lNameText = (EditText) findViewById(R.id.lNameText);
        hugSwitch = (Switch) findViewById(R.id.hugSwitch);
        hFiveSwitch = (Switch) findViewById(R.id.hFiveSwitch);
        motivationSwitch = (Switch) findViewById(R.id.motivationSwitch);
        result = "";

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        String signUpSendURL = signUpURL + "/?eMail=" + emailText.getText() + "&FirstName=" +
                fNameText.getText() + "&LastName=" + lNameText.getText() + "&VolunteerHug=0"  +
                "&VolunteerHighFive=0" + "&VolunteerMotivate=0" +
                "&password=" + passwordText.getText();

        if(passwordText == passwordConText){

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    signUpSendURL, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String responder = response.getString("response");
                        result = result+responder;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonObjectRequest);



            if (result == "ok"){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            else if(result == "error"){

            }
            else if(result == "exists"){

            }
        }
        else{

        }

    }
}
