package com.example.emotionalsupportapp.Highfive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.MainActivity;
import com.example.emotionalsupportapp.R;
import com.example.emotionalsupportapp.Service.RequestsListAdapter;
import com.example.emotionalsupportapp.Service.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HighFiveVolunteerActivity extends AppCompatActivity {

    private static final String phpurl = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/";

    private JSONArray users;
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<User> userList;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_five_volunteer);
        mList = findViewById(R.id.high_five_request_list);
        userList = new ArrayList<>();
        adapter = new RequestsListAdapter(getApplicationContext(),userList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(),linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        String query = "";
        String phpfile = "allActiveUsers.php";
        getData(phpfile,query);

        super.onStart();
    }

    public void returnHome(View v){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }
    public void confirmRequest(View v){
        Intent returnToMainIntent = new Intent(this, MainActivity.class);
        startActivity(returnToMainIntent);
    }

    public void getData(String phpfile,String query){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringBuffer fullURL = new StringBuffer();
        fullURL.append(phpurl);
        fullURL.append(phpfile);
        fullURL.append(query);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(fullURL.toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    Log.e("Success", String.valueOf(response));

                    for(int i = 0; i < response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String username = jsonObject.getString("userID");
                        String lat = jsonObject.getString("xCord");
                        String lon = jsonObject.getString("yCord");
                        User user = new User(username,lat,lon);
                        userList.add(user);
                    }
                }catch(JSONException e){
                    Log.e("JSON Error",e.toString());
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley",error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}