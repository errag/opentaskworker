package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.net.Proxy;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class HeadsetSensor extends Sensor {
    @Override
    public String getActionName() {
        return Intent.ACTION_HEADSET_PLUG;
    }

    @Override
    public String getState(Context context, Intent intent) {
        return State.Headset.PLUG.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_headset;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.headset_plug, State.Headset.PLUG.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
