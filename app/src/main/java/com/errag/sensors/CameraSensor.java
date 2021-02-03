package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class CameraSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.hardware.action.NEW_PICTURE";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        return params.testValue(State.Camera.SHOT.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_camera;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.camera_snap, State.Camera.SHOT.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
