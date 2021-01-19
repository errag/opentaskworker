package com.spif.models;

import android.app.AlarmManager;

import java.util.Calendar;

public class TimerObject
{
    enum ALARM_TYPE {
        NONE,
        ON_INTERVAL,
        ON_DAY_OF_WEEK,
        ON_DAY_OF_MONTH
    }

    private ALARM_TYPE alarmType = ALARM_TYPE.NONE;
    private int hourOfDay = 0;
    private int minute = 0;
    private int repeatInterval = 0;

    private TimerObject(ALARM_TYPE _alarmType, int _repeatInterval)
    {
        this.alarmType = _alarmType;
        this.repeatInterval = _repeatInterval;
    }

    public static TimerObject getAlarmObjectInterval(int _repeatInterval)
    {
        return new TimerObject(ALARM_TYPE.ON_INTERVAL, _repeatInterval);
    }

    public Calendar getCalendar()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if(!this.alarmType.equals(ALARM_TYPE.ON_INTERVAL))
        {
            calendar.set(Calendar.HOUR_OF_DAY, this.hourOfDay);
            calendar.set(Calendar.MINUTE, this.minute);
        }

        return calendar;
    }

    public long getRepeat() {
        return (this.alarmType.equals(ALARM_TYPE.ON_INTERVAL) ? this.repeatInterval : AlarmManager.INTERVAL_DAY);
    }
}
