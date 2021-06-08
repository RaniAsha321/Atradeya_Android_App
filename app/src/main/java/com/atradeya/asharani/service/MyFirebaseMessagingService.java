package com.atradeya.asharani.service;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.atradeya.asharani.App_Main;
import com.atradeya.asharani.app.Config;
import com.atradeya.asharani.util.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import io.paperdb.Paper;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String millisInString  = dateFormat.format(new Date());

                handleDataMessage(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), remoteMessage.getData().get("sound"), millisInString);

        }

    }
    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        }
        else{

            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(String title, String body, String sound, String time) {

        String timestamp3=Paper.book().read("timeStamp");
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {


            if((timestamp3==null) || (!timestamp3.equals(time))){
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", body);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            showNotificationMessage(getApplicationContext(),title,body,time,pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }

            else {

                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", body);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                showNotificationMessage(getApplicationContext(),title,body,time,pushNotification);
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }

        }else {
            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), App_Main.class);
            resultIntent.putExtra("message", body);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           notificationUtils.showNotificationMessage(getApplicationContext(),title, message, timeStamp, intent);

    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */

}
