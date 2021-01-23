package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class ScreenOffSensor extends Sensor {

    @Override
    public String getActionName() {
        return Intent.ACTION_SCREEN_OFF;
    }

    @Override
    public String getState(Context context, Intent intent) {
        return State.ScreenOff.OFF.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_screenoff;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.screen_off, State.ScreenOff.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
