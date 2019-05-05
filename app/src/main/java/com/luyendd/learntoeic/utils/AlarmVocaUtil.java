package com.luyendd.learntoeic.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.luyendd.learntoeic.obj.Voca;
import com.luyendd.learntoeic.service.SchedulingVocaService;

import java.util.Calendar;

public class AlarmVocaUtil {
    public static PendingIntent pendingIntent;
    public static AlarmManager alarmManager;

    public static void initAlarm(Context context, Voca voca) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SchedulingVocaService.class);
        intent.putExtra(Const.VOCA, voca);
        pendingIntent =
                PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static void turnOnAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 1, pendingIntent);

    }

    public static void turnOffAlarm() {
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }


}
