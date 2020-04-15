package com.example.emotionalsupportapp.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONArray;

public class QueryDatabase {


    private static final String phpurl = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/";

    /**
     *      Use this function to make pull request for data from the server, not expecting data to be updated in this request
     *
     * @param context   The context used to call this function (Can use getApplicationContext())
     * @param phpfile   The php file you want to be executed when this function is called
     * @param query     The query string to pass to this php file if there is any (Must start with ? for first key,value pair then use & for remaining)
     * @return          This function returns a json array of data from the server
     */
    public static JSONArray pullGroupFromDatabase(Context context, String phpfile, String query){
        RequestQueue reqQueue = Volley.newRequestQueue(context);
        StringBuffer fullURL = new StringBuffer();
        fullURL.append(phpurl);
        fullURL.append(phpfile);
        fullURL.append(query);
        final JSONArray[] result = new JSONArray[1];
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, fullURL.toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Respond", String.valueOf(response));
                result[0] = response;
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
               Log.e("VolleyError",error.toString());

            }
        });
        reqQueue.add(jsonObjectRequest);
        return result[0];
    }

}

