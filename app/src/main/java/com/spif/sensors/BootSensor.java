package com.spif.sensors;

import android.content.Context;
import android.content.Intent;

import com.spif.models.Parameter;
import com.spif.models.Sensor;
import com.spif.models.State;
import com.spif.opentaskworker.R;

public class BootSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.intent.action.BOOT_COMPLETED";
    }

    @Override
    public String getState(Context context, Intent intent) {
        return State.Boot.UP.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_boot;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.boot_up, State.Boot.UP.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
