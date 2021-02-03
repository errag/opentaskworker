package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class BootSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.intent.action.BOOT_COMPLETED";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        return params.testValue(State.Boot.UP.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_boot;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.boot_up, State.Boot.UP.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
