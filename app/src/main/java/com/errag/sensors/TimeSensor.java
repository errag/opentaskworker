package com.errag.sensors;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

import java.util.Calendar;

public class TimeSensor extends Sensor {
    private AlarmManager alarmManager = null;
    private PendingIntent alarmIntent = null;

    private static Integer ALARM_ID = 1000;

    @Override
    public String getActionName() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void registrateCustomIntentFilter(Context context) {
        int repeatInterval = Action.getACInteger(State.TIME.INTERVAL.toString(), this.getInputParameters());

        if(alarmManager != null && alarmManager != null)
            alarmManager.cancel(alarmIntent);
        else
            alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public String getState(Context context, Intent intent) {
        return null;
    }

    @Override
    public int getImage() {
        return R.drawable.img_calendar;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.time_interval, State.TIME.INTERVAL.toString(), Parameter.Type.INTEGER)
        };
    }
}
