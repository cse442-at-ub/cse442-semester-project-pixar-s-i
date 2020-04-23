package com.example.emotionalsupportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.emotionalsupportapp.Connect.ConnectActivity;
import com.example.emotionalsupportapp.Motivation.MotivationActivity;
import com.example.emotionalsupportapp.Settings.MessageNotification;
import com.example.emotionalsupportapp.Talk.TalkActivity;
import com.example.emotionalsupportapp.Highfive.HighFiveActivity;
import com.example.emotionalsupportapp.Hug.HugActivity;
import com.example.emotionalsupportapp.Member.Profile.profilePage;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String userID;
    TextView hasUnseenMessages;
    private final String MESSAGE_CHANNEL_ID = "message_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = getIntent().getExtras().getString("EXTRA_USER_ID");

        createNotificationChannel();
        hasUnseenMessages = findViewById(R.id.has_unseen_messages);
        checkUnseenMessage(hasUnseenMessages);
    }

    /*
     * Changes to the main high five page
     * should be used to change to the high five page
     * Called when high five button is clicked
     */
    public void goToHighFivePage(View view){
        Intent intent = new Intent(this, HighFiveActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main hug page
     * should be used to change to the hug page
     * Called when hug button is clicked
     */
    public void goToHugPage(View view){
        Intent intent = new Intent(this, HugActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Motivation page
     * should be used to change to the Motivation page
     * Called when Motivation button is clicked
     */
    public void goToMotivationPage(View view){
        Intent intent = new Intent(this, MotivationActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Talk page
     * should be used to change to the Talk page
     * Called when talk button is clicked
     */
    public void goToTalkPage(View view){
        Intent intent = new Intent(this, TalkActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Connect page
     * should be used to change to the Connect page
     * Called when Connect button is clicked
     */
    public void goToConnectPage(View view){
        Intent intent = new Intent(this, ConnectActivity.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }
    /*
     * Changes to the main Profile page
     * should be used to change to the Profile page
     * Called when Profile button is clicked
     */
    public void goToProfilePage(View view){
        Intent intent = new Intent(this, profilePage.class);
        intent.putExtra("EXTRA_USER_ID", userID);
        startActivity(intent);
    }

    public void checkUnseenMessage(final TextView hasUnseenMessages){
        String phpURLBase = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442e/checkUnseenMessages.php/?" +
                "user_id=" + userID;
        RequestQueue reqQueue;
        reqQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, phpURLBase, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String check = response.getString("response");
                    if (check.equals("exist")){
                        hasUnseenMessages.setText("!");
                        showMessageNotification();
                    }else{
                        hasUnseenMessages.setText("");
                    }
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel messageChannel = new NotificationChannel(
                    MESSAGE_CHANNEL_ID,
                    "Message Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            messageChannel.setDescription("This is Message Channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(messageChannel);
        }
    }

    private void showMessageNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                MainActivity.this, MESSAGE_CHANNEL_ID
        )
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("New Notification")
                .setContentText("New Message")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0,builder.build());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        checkUnseenMessage(hasUnseenMessages);
    }
}
