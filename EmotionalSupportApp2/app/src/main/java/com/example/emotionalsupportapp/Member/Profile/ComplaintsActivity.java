package com.example.emotionalsupportapp.Member.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    public String idNum = "1";

    public String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getInfoProfile.php/?id=" + idNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        idNum = getIntent().getStringExtra("EXTRA_USER_ID");

        phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/getInfoProfile.php/?id=" + idNum+ "&meetingType=0";

     //   submit =  findViewById(R.id.submit_button);
       // submit.setOnClickListener(new View.OnClickListener() {
      //      @Override
       //     public void onClick(View view){

      //      }
     //   });
    }
}
