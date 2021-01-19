package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class AirplainModeSensor extends Sensor {

    @Override
    public String getActionName() {
        return Intent.ACTION_AIRPLANE_MODE_CHANGED;
    }

    @Override
    public String getState(Context context, Intent intent) {
        State.AirplainMode state = State.AirplainMode.OFF;
        boolean isAirplaneMode = intent.getBooleanExtra("state", false);

        if(isAirplaneMode)
            state = State.AirplainMode.ON;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_airplane;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.airplane_mode_on, State.AirplainMode.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.airplane_mode_off, State.AirplainMode.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
