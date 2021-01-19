package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.spif.opentaskworker.R;

public class OrientationSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.intent.action.CONFIGURATION_CHANGED";
    }

    @Override
    public String getState(Context context, Intent intent) {
        State.Orientation state = State.Orientation.PORTRAIT;

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            state = State.Orientation.LANDSCAPE;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_rotate;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.orientation_portrait, State.Orientation.PORTRAIT.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.orientation_landscape, State.Orientation.LANDSCAPE.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
