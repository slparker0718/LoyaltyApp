package edu.psu.slparker.loyaltyapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private String CHANNEL_NAME = "NotificationBroadcastReceiver";
    private String CHANNEL_DESCRIPTION = "NotificationBroadcastReceiver";
    private Notification.Builder notificationBuilder;

    public static final String NOTIFICATION_BROADCAST_RECEIVER = "edu.psu.slparker.loyaltyapp.action.NotificationBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get data from intent
        String notificationType = intent.getStringExtra("NOTIFICATION_TYPE");
        Integer channel_id = intent.getIntExtra("ID", 0);

        //instantiate notification manager
        NotificationManager notificationManager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationType.equals("coupon"))
        {
            Intent resultIntent = new Intent(context, WelcomeActivity.class);
            resultIntent.putExtra("NOTIF_KEY", "Coupon");
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder = new Notification.Builder(context, CHANNEL_NAME).setSmallIcon(R.drawable.ic_menu_coupons).setContentTitle("New Starbucks Coupons Available").setContentText("You have one or more coupons available for your nearby Starbucks Store!").setAutoCancel(true).setContentIntent(resultPendingIntent);
        }
        else
        {
            Intent resultIntent = new Intent(context, WelcomeActivity.class);
            resultIntent.putExtra("NOTIF_KEY", "NearbyStore");
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder = new Notification.Builder(context, CHANNEL_NAME).setSmallIcon(R.drawable.ic_menu_stores).setContentTitle("Starbucks Store Near You!").setContentText("You are nearby one of our Starbucks stores! Stop in and say hello!").setAutoCancel(true).setContentIntent(resultPendingIntent);
        }

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_NAME, CHANNEL_DESCRIPTION, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setShowBadge(true);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationChannel.enableVibration(true);
        notificationChannel.enableLights(true);

        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(channel_id, notificationBuilder.build());
    }
}
