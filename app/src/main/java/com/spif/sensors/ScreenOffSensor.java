package com.spif.sensors;

import android.content.Context;
import android.content.Intent;

import com.spif.models.Parameter;
import com.spif.models.Sensor;
import com.spif.models.State;
import com.spif.opentaskworker.R;

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
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.screen_off, State.ScreenOff.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
