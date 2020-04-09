package com.example.emotionalsupportapp.Service;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData()!=null){

        }

    }

//    private void sendNotification(Bitmap bitmap){
//
//
//        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
//        style.bigPicture(bitmap);
//
//        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
//
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        String NOTIFICATION_CHANNEL_ID = "101";
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);
//
//            //Configure Notification Channel
//            notificationChannel.setDescription("Game Notifications");
//            notificationChannel.enableLights(true);
//            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
//            notificationChannel.enableVibration(true);
//
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setContentTitle(Config.title)
//                .setAutoCancel(true)
//                .setSound(defaultSound)
//                .setContentText(Config.content)
//                .setContentIntent(pendingIntent)
//                .setStyle(style)
//                .setLargeIcon(bitmap)
//                .setWhen(System.currentTimeMillis())
//                .setPriority(Notification.PRIORITY_MAX);
//
//
//        notificationManager.notify(1, notificationBuilder.build());
//
//
//    }

}
