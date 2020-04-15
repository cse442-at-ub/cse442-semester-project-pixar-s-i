package com.example.emotionalsupportapp.Talk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TalkActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    String userID = getIntent().getStringExtra("EXTRA_USER_ID");
    public String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getInfoProfile.php/?id=" + userID;
    public RequestQueue reqQueue;
    String number = "111111111";
    String emergencyNumber = "(800)273-8255";
//    String emergencyNumberPlaceholder = "(555)427-2999";

//    StateListener phoneStateListener;
   // Bundle savedInstanceState;


    public TalkActivity() {
       // super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_talk);

        initializeSql();
//        phoneStateListener = new StateListener();
//        TelephonyManager telephonyManager =(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
//        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    /*
     * Changes to the main high five page
     * should be used to change to the high five page
     * Called when high five button is clicked
     */
    public void goToHomePage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }

    public void dialButton_onClick(View view) {
        makePhoneCall();
    }
    public void hotlineButton_onClick(View view) {
        makeEmergencyPhoneCall();
    }

    public void makePhoneCall() {


        Log.d("Number Updated:", number);
        Intent intent = new Intent(Intent.ACTION_CALL);
        if(number != null){
            intent.setData(Uri.parse("tel: " + number));

        }else{
            intent.setData(Uri.parse("tel: " + "(111)1111111"));

        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TalkActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            return;
        }
        startActivity(intent);
//        String toPrint = phoneStateListener.currentState + ", Prev State = " + phoneStateListener.prevState;
//        Log.d("STATE: ", toPrint);

        Log.d("ROR: ", "EXECUTED UNNESSICARILY");

    }

    public void makeEmergencyPhoneCall() {


        Log.d("Number Updated:", number);
        Intent intent = new Intent(Intent.ACTION_CALL);
        //number
        intent.setData(Uri.parse("tel: " + emergencyNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TalkActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            return;
        }
        startActivity(intent);
//        String toPrint = phoneStateListener.currentState + ", Prev State = " + phoneStateListener.prevState;
//        Log.d("STATE: ", toPrint);

        Log.d("ROR: ", "EXECUTED UNNESSICARILY");

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initializeSql() {
        reqQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray prof = response.getJSONArray("profiles");

                    number = prof.getJSONObject(0).getString("emergencyContact");


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
}