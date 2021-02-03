package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class USBSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.hardware.usb.action.USB_STATE";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        boolean usbState = intent.getExtras().getBoolean("connected");
        State.USB state = (usbState ? State.USB.CONNECTED : State.USB.DISCONNECTED);

        return params.testValue(state.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_usb;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.usb_connected, State.USB.CONNECTED.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.usb_disconnected, State.USB.DISCONNECTED.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
