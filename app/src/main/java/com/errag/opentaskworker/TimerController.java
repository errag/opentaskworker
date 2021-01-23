package com.errag.opentaskworker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.errag.models.TimerObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimerController {
    private AlarmManager alarmManger = null;
    private List<PendingIntent> alarmIntents = new ArrayList<>();
    private List<TimerObject> alarms = null;

    public void start(Context context, List<TimerObject> _alarms)
    {
        this.alarms = _alarms;

        if(alarmManger != null && alarmIntents != null)
        {
            for(PendingIntent alarmIntent : alarmIntents)
                alarmManger.cancel(alarmIntent);

            alarmIntents.clear();
        }
        else
            alarmManger = getAlarmManger(context);

        for(TimerObject alarm : alarms)
        {
            PendingIntent alarmIntent = getAlarmIntent(context, alarmIntents.size());
            Calendar calendar = alarm.getCalendar();

            alarmManger.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarm.getRepeat(), alarmIntent);
            System.out.println("Repeat " + alarm.getRepeat());
        }
    }

    private static AlarmManager getAlarmManger(Context context)
    {
        return (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    private static PendingIntent getAlarmIntent(Context context, int requestId)
    {
        //Intent intent = new Intent(context, EventController.class);
        //intent.putExtra("INDEX", requestId);

        //return PendingIntent.getBroadcast(context, requestId, intent, 0);
        return null;
    }
}
