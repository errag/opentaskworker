package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class ButtonSensor extends Sensor {

    @Override
    public String getActionName() {
        return null;
    }

    @Override
    public String[] getActionNames() {
        return new String[] {
            Intent.ACTION_CALL_BUTTON,
            Intent.ACTION_CAMERA_BUTTON,
            Intent.ACTION_MEDIA_BUTTON
        };
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        State.Button state = State.Button.NONE;

        if(intent.getAction().equals(Intent.ACTION_CALL_BUTTON))
            state = State.Button.CALL;
        else if(intent.getAction().equals(Intent.ACTION_CAMERA_BUTTON))
            state = State.Button.CAMERA;
        else if(intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON))
            state = State.Button.MEDIA;

        return params.testValue(state.toString(), true);
    }


    @Override
    public int getImage() {
        return R.drawable.img_button;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.button_call, State.Button.CALL.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.button_camera, State.Button.CAMERA.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.button_media, State.Button.MEDIA.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
