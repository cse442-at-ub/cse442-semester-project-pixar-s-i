package com.example.emotionalsupportapp.Service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.Highfive.HighFiveActivity;
import com.example.emotionalsupportapp.R;

import java.util.HashMap;
import java.util.Map;

public class CancelHighFiveDialog extends AppCompatDialogFragment {

    private String userId;
    public CancelHighFiveDialog(String userID){
        userId = userID;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Request Cancellation").setMessage("Are you sure you want to cancel High Five?");

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelRequest(userId);
                Intent main = new Intent(getActivity(),HighFiveActivity.class);
                main.putExtra("EXTRA_USER_ID",userId);
                startActivity(main);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
            return builder.create();
    }

    //Cancel Request and Update User location to indicate request has been cancelled
    public void cancelRequest(final String userID){

        String phpfile = "removeUserFromMatchedTB.php";
        String result = "";
        StringBuilder fullURL = new StringBuilder();
        fullURL.append(getString(R.string.database_url));
        fullURL.append(phpfile);

        StringRequest removeUserRequest = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                HashMap<String,String> query = new HashMap<>();
                query.put("userID", userID);
                return query;
            }
        };
         phpfile = "updateCoord.php";
        fullURL = new StringBuilder();
        fullURL.append(getString(R.string.database_url));
        fullURL.append(phpfile);

        StringRequest updateLocation = new StringRequest(Request.Method.POST, fullURL.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> query = new HashMap<>();
                query.put("userID",userID);
                query.put("xCord","200");
                query.put("yCord","200");
                return query;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(removeUserRequest);
        requestQueue.add(updateLocation);
    }
}
