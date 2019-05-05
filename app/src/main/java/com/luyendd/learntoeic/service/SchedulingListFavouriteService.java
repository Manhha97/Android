package com.luyendd.learntoeic.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.activity.VocaDetailsActivity;
import com.luyendd.learntoeic.utils.Const;

public class SchedulingListFavouriteService extends IntentService {

    private static final int TIME_VIBRATE = 1000;

    public SchedulingListFavouriteService() {
        super(SchedulingListFavouriteService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Scheduling Service", "[running notification]");
        Intent notificationIntent = new Intent(this, VocaDetailsActivity.class);
        notificationIntent.putExtra(Const.TOPIC_FAVOURITE, true);
        notificationIntent.putExtra(Const.TOPIC_NAME, "Favourite");
        notificationIntent
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent
                .getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("Learn voca favourite")
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setPriority(6)
                        .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
                                TIME_VIBRATE})
                        .setContentIntent(contentIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}

