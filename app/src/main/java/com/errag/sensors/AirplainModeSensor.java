package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class AirplainModeSensor extends Sensor {

    @Override
    public String getActionName() {
        return Intent.ACTION_AIRPLANE_MODE_CHANGED;
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        boolean isAirplaneMode = intent.getBooleanExtra("state", false);
        State.AirplainMode state = (isAirplaneMode ? State.AirplainMode.ON : State.AirplainMode.OFF);

        return params.testValue(state.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_airplane;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.airplane_mode_on, State.AirplainMode.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.airplane_mode_off, State.AirplainMode.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
