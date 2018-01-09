package eu.biketrack.android.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import eu.biketrack.android.R;
import eu.biketrack.android.activities.BikeTrack;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        try {
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Data: " + remoteMessage.getData());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage);
        } catch (Exception e) {
            Log.e(TAG, "onMessageReceived: ", e);
        }


    }

    private void sendNotification(String messageTitle, String messageBody, RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, BikeTrack.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {

            Bundle bundle = new Bundle();
            bundle.putString("latitude", remoteMessage.getData().get("latitude"));
            bundle.putString("longitude", remoteMessage.getData().get("longitude"));
            bundle.putString("tarckerId", remoteMessage.getData().get("tarckerId"));
            bundle.putString("type", remoteMessage.getData().get("type"));
            intent.putExtras(bundle);
        } catch (Exception e) {
            Log.e(TAG, "sendNotification: ", e);
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500, 500, 500, 500, 500};

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.biketrack)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE, 1, 1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
