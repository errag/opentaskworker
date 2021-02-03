package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class OrientationSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.intent.action.CONFIGURATION_CHANGED";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        boolean isLandscape = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        State.Orientation state = (!isLandscape ? State.Orientation.PORTRAIT : State.Orientation.LANDSCAPE);

        return params.testValue(state.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_rotate;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.orientation_portrait, State.Orientation.PORTRAIT.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.orientation_landscape, State.Orientation.LANDSCAPE.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
