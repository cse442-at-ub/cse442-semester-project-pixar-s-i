package com.example.emotionalsupportapp.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.emotionalsupportapp.Highfive.HighFiveActivity;
import com.example.emotionalsupportapp.Hug.HugActivity;
import com.example.emotionalsupportapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    public static final int ID_SMALL_NOTIFICATION = 235;
    private String requester;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            try {
                requester = jsonObject.getString("userID");
            } catch (JSONException e) {
                Log.e("Data Load Error",jsonObject +" :" + e);
            }
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG,"Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

    }


    private void sendNotification(String title,String messageBody) {
        Intent intent;
        SharedPreferences sp;
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        if (sp.getBoolean("Login", false)) {
            String userID = sp.getString("userID", "");
            String username = sp.getString("username","");

            Log.e("UserID Notif", userID + " " + requester);

            if (!userID.equals(requester)){
                if (title.equals("Hug Request") && sp.getBoolean("HugNotify",true)) {
                    intent = new Intent(this, HugActivity.class);
                } else if(sp.getBoolean("HighFiveNotify",true)) {
                    intent = new Intent(this, HighFiveActivity.class);
                }else{
                    return;
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXTRA_USER_ID", userID);
                intent.putExtra("EXTRA_USERNAME",username);
                intent.putExtra("notification", true);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                String channelId = getString(R.string.common_google_play_services_notification_channel_name);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(R.drawable.ic_notifications_24)
                                .setContentTitle(title)
                                .setContentText(messageBody)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            }
        }
    }

}
