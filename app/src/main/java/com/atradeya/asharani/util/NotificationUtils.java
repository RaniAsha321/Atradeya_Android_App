package com.atradeya.asharani.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import com.atradeya.asharani.R;
import com.atradeya.asharani.app.Config;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(Context applicationContext, String title, String message, String timeStamp, Intent intent) {
        showNotificationMessage(title, message, timeStamp, intent);
    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent) {
        // Check for empty push message

        Log.e("tag","6");
        if (TextUtils.isEmpty(message))


            return;
        Log.e("tag","7");

        String timestamp2= Paper.book().read("timestamp");



          // notification icon
          final int icon = R.drawable.fevicon_iocn;

          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
          final PendingIntent resultPendingIntent =
                  PendingIntent.getActivity(
                          mContext,
                          0,
                          intent,
                          PendingIntent.FLAG_CANCEL_CURRENT
                  );

          final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                  mContext);

          final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                  + "://" + mContext.getPackageName() + "/raw/eventually");

          Log.e("tag", "9");


          showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);


          playNotificationSound();

    }

    @SuppressLint("ResourceAsColor")
    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        String  CHANNEL_ID = "my_channel_01";

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            notificationManager.createNotificationChannel(channel);
        }
        Paper.book().write("timestamp",timeStamp);
        Log.e("time",timeStamp+"");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(message);



        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setLights(ContextCompat.getColor(mContext, R.color.bluetheme), 3000, 3000)
                .setStyle(inboxStyle)
                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(R.drawable.fevicon_iocn,R.color.bluetheme)
                .setColor(R.color.white)
                .setDefaults(
                        Notification.DEFAULT_SOUND
                                | Notification.DEFAULT_VIBRATE
                                | Notification.DEFAULT_LIGHTS
                )
                .setLights(Color.MAGENTA, 500, 500)

                .setChannelId(CHANNEL_ID)
                .setContentText(message)

                .build();

        notificationManager.notify((int) System.currentTimeMillis(), notification);


    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setLights(ContextCompat.getColor(mContext, R.color.bluetheme), 3000, 3000)
                .setStyle(bigPictureStyle)
                .setWhen(getTimeMilliSec(timeStamp))
                .setDefaults(
                        Notification.DEFAULT_SOUND
                                | Notification.DEFAULT_VIBRATE
                                | Notification.DEFAULT_LIGHTS
                )
                .setLights(Color.MAGENTA, 500, 500)
                .setSmallIcon(R.drawable.icon_)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {

            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/eventually");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static long getTimeMilliSec(String timeStamp) {

        Log.e("time",timeStamp+"");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);

            Log.e("time",timeStamp+"");
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
