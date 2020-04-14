package com.example.emotionalsupportapp.Service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PhPHighFiveService {

    static String baseUrl = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/";
    static String addToActiverUserRequest = "writeCoord.php";
    static String allActiveUsersRequest = "allActiveUsers.php";


    //first method will return a matchedUser object that will return a users ID and Coordinates
    static UserData addToActiverUserTb (RequestQueue queue, final int userID, final double xCoord, final double yCoord){
        String url = baseUrl + addToActiverUserRequest;
        final UserData data = new UserData(-1,0.0,0.0);
        StringRequest strRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);


//
//                System.out.println("This os the returned response "+ response.toString());
//                try {
//                    JSONArray results = new JSONArray(response);
//                    JSONObject obj1 = results.getJSONObject(0);
//                    int id = obj1.getInt("userID");
//                    JSONObject obj2 = results.getJSONObject(1);
//                    double xCord = obj2.getDouble("xCord");
//                    JSONObject obj3 = results.getJSONObject(3);
//                    double yCord = obj3.getDouble("yCord");
//                    data.matchedUser_ID = id;
//                    data.xCoord = xCord;
//                    data.yCoord = yCord;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
////                         for(int x = 0; x <results.length(); x++ ){
////                             JSONObject obj = results.getJSONObject(x);
////                             int id = obj.getInt("userID");
////                             double xCord = obj.getDouble("xCord");
////                             double yCord = obj.getDouble("yCord");
////                             data.matchedUser_ID = id;
////                             data.xCoord = xCord;
////                             data.yCoord = yCord;
////                     }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map <String,String> parms = new HashMap<String, String>();
                parms.put("userID",Integer.toString(userID));
                parms.put("xCord",Double.toString(xCoord));
                parms.put("yCord",Double.toString(yCoord));
                return parms;
            }
        };
        queue.add(strRequest);
        return data;
    }


}
