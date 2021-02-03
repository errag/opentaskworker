package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class HeadsetSensor extends Sensor {
    @Override
    public String getActionName() {
        return Intent.ACTION_HEADSET_PLUG;
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        int headsetState = intent.getIntExtra("state", -1);
        State.Headset state = (headsetState == 0 ? State.Headset.PLUG : headsetState == 1 ? State.Headset.UNKNOWN : State.Headset.UNKNOWN);

        return params.testValue(state.toString(), true);
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
