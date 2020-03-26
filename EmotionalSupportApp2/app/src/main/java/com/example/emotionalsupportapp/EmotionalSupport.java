package com.example.emotionalsupportapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

//Wraps all application set up in this
public class EmotionalSupport extends Application {
    /*
        Hold the notification information for each feature
        This is where you set a new notification using a different channel name and number
     */
    public static final String CHANNEL_1_ID = "HighFivenNotification";
    public static final String CHANNEL_2_ID = "HugNotification";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

//    Checks if notification channels will works on sdk version and then creates it
    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,   //Channel Id
                    "High Five Notification", //Name user will see
                    NotificationManager.IMPORTANCE_DEFAULT // Importance Level
            );
            channel1.setDescription("This is the notification for requests from users who want a high five");
            NotificationChannel channel2 = new NotificationChannel(
              CHANNEL_2_ID,
              "Hug Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel2.setDescription("This is the notification for requests from users who want a hug");
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel1);
        manager.createNotificationChannel(channel2);
        }
    }

}
