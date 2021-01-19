package com.spif.sensors;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.spif.models.Parameter;
import com.spif.models.Sensor;
import com.spif.models.State;
import com.spif.opentaskworker.R;

public class InterruptionSensor extends Sensor {
    @Override
    public String getActionName() {
        return NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public String getState(Context context, Intent intent) {
        State.Interruption state = State.Interruption.NOT_RESOLVEABLE;
        NotificationManager notifi = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(notifi.getCurrentInterruptionFilter() == NotificationManager.INTERRUPTION_FILTER_ALARMS)
            state = State.Interruption.ALARMS;
        else if (notifi.getCurrentInterruptionFilter() == NotificationManager.INTERRUPTION_FILTER_NONE)
            state = State.Interruption.NONE;
        else if (notifi.getCurrentInterruptionFilter() == NotificationManager.INTERRUPTION_FILTER_ALL)
            state = State.Interruption.ALL;
        else if (notifi.getCurrentInterruptionFilter() == NotificationManager.INTERRUPTION_FILTER_PRIORITY)
            state = State.Interruption.PRIORITY;
        else if (notifi.getCurrentInterruptionFilter() == NotificationManager.INTERRUPTION_FILTER_UNKNOWN)
            state = State.Interruption.UNKNOWN;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_interruption;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.interruption_all, State.Interruption.ALL.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.interruption_priority, State.Interruption.PRIORITY.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.interruption_alarms, State.Interruption.ALARMS.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.interruption_none, State.Interruption.NONE.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.interruption_unknown, State.Interruption.UNKNOWN.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
