package com.luyendd.learntoeic.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.luyendd.learntoeic.ConnectDataBase;
import com.luyendd.learntoeic.obj.Voca;
import com.luyendd.learntoeic.service.SchedulingListFavouriteService;
import com.luyendd.learntoeic.service.SchedulingVocaService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AlarmUtil {

    public static PendingIntent pendingIntent;
    public static AlarmManager alarmManager;
    public static List<Voca> vocaFavorite = new ArrayList<>();
    public static ConnectDataBase cdb;
    static final String TAG = "AlarmUtil";

    public static boolean initAlarm(Context context, boolean isNoticeFavourite) {
        try {
            cdb = new ConnectDataBase(context);
            vocaFavorite = cdb.getListFavorite();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (vocaFavorite.size() != 0) {

            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent;
            if (isNoticeFavourite) {
                intent = new Intent(context, SchedulingListFavouriteService.class);

            } else {
                intent = new Intent(context, SchedulingVocaService.class);
            }

            pendingIntent =
                    PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return true;
        } else {
            Toast.makeText(context, "Voca Favourite is null", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public static void turnOnAlarm(Context context, boolean isNoticeFavorite) {
        Log.d(TAG, "[turn On Alarm]");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (isNoticeFavorite) {
            int hour = sharedPreferences.getInt(Const.ALARM_LIST_FAVORITE_HOUR, 10);
            int minute = sharedPreferences.getInt(Const.ALARM_LIST_FAVORITE_MINUTE, 10);
            Log.d(TAG, "[hour] : " + hour + " : " + minute);
            // Set the alarm to start at approximately 2:00 p.m.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            if (Calendar.getInstance().after(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            // With setInexactRepeating(), you have to use one of the AlarmManager interval
            // constants--in this case, AlarmManager.INTERVAL_DAY.
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager
//                    .setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        } else {
//            alarmManager
//                    .set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        }
        } else {
            int minute = sharedPreferences.getInt(Const.ALARM_VOCA_MINUTE_REPEAT, 2);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                    1000 * 60 * minute, pendingIntent);

        }

    }

    public static void turnOffAlarm() {
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "[Turn Off Alarm]");
    }

}
