package com.spif.sensors;

import android.content.Context;
import android.content.Intent;

import com.spif.models.Parameter;
import com.spif.models.Sensor;
import com.spif.models.State;
import com.spif.opentaskworker.R;

public class ScreenOnSensor extends Sensor {

    @Override
    public String getActionName() {
        return Intent.ACTION_SCREEN_ON;
    }

    @Override
    public String getState(Context context, Intent intent) {
        return State.ScreenOn.ON.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_screenon;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.screen_on, State.ScreenOn.ON.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
