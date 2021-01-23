package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

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
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.screen_on, State.ScreenOn.ON.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
