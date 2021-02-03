package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
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
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        Boolean screenState = intent.getAction().equals(Intent.ACTION_SCREEN_ON);
        String state = (screenState ? State.ScreenOn.ON : State.ScreenOff.OFF).toString();

        return params.testValue(state, true);
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
