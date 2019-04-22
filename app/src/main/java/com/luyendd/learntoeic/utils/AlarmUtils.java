package com.luyendd.learntoeic.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.luyendd.learntoeic.obj.Voca;
import com.luyendd.learntoeic.service.SchedulingService;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AlarmUtils {
    private static int INDEX = 1;

    public static void create(Context context, List<Voca> vocaList) {
        Voca voca = vocaList.get(0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SchedulingService.class);
            intent.putExtra(Const.VOCA, voca);
            PendingIntent pendingIntent =
                    PendingIntent.getService(context, INDEX, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, INDEX);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager
                        .setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager
                        .set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
    }
}
