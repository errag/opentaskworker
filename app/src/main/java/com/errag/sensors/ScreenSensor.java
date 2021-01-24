package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class ScreenSensor extends Sensor {

    @Override
    public String getActionName() {
        return null;
    }

    @Override
    public String[] getActionNames() {
        return new String[] {
            Intent.ACTION_SCREEN_ON,
            Intent.ACTION_SCREEN_OFF
        };
    }

    @Override
    public String getState(Context context, Intent intent) {
        String state = null;

        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
            state = State.ScreenOn.ON.toString();
        else
            state = State.ScreenOff.OFF.toString();

        return state;
    }

    @Override
    public int getImage() {
        return R.drawable.img_screenon;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.screen_on, State.ScreenOn.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.screen_off, State.ScreenOff.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
