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

import com.luyendd.learntoeic.ConnectDataBase;
import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.activity.MainActivity;
import com.luyendd.learntoeic.obj.Voca;
import com.luyendd.learntoeic.utils.Const;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SchedulingVocaService extends IntentService {

    public static ConnectDataBase cdb;
    public static List<Voca> vocaFavorite = new ArrayList<>();
    private static final int TIME_VIBRATE = 1000;

    public SchedulingVocaService() {
        super(SchedulingListFavouriteService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Scheduling Service", "[running notification]");
        try {
            cdb = new ConnectDataBase(this);
            vocaFavorite = cdb.getListFavorite();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Voca voca = vocaFavorite.get(new Random().nextInt(vocaFavorite.size()));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent
                .getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(voca.getVocabulary() + " : " + voca.getTranslate() )
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
