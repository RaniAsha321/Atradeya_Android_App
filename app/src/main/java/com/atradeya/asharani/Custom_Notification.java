package com.atradeya.asharani;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;

public class Custom_Notification extends AppCompatActivity {

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_login);

        int icon = R.drawable.ic_launcher_background;
        long when = System.currentTimeMillis();
        android.app.Notification notification = new android.app.Notification(icon, "Custom Custom_Notification", when);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);

        notification.contentView = contentView;

        Intent notificationIntent = new Intent(this, App_Main.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.contentIntent = contentIntent;

        notification.flags |= android.app.Notification.FLAG_NO_CLEAR; //Do not clear the com.example.scorpsoft.Custom_Notification
        notification.defaults |= android.app.Notification.DEFAULT_LIGHTS; // LED
        notification.defaults |= android.app.Notification.DEFAULT_VIBRATE; //Vibration
        notification.defaults |= android.app.Notification.DEFAULT_SOUND; // Sound

        mNotificationManager.notify(1, notification);

    }
}
