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
        State.Headset state = State.Headset.UNKNOWN;

        int headsetState = intent.getIntExtra("state", -1);

        if(headsetState == 0)
            state = State.Headset.PLUG;
        else if(headsetState == 1)
            state = State.Headset.UNPLUG;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_headset;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.headset_plug, State.Headset.PLUG.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.headser_unplug, State.Headset.UNPLUG.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
