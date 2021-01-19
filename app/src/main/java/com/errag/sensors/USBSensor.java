package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.spif.opentaskworker.R;

public class USBSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.hardware.usb.action.USB_STATE";
    }

    @Override
    public String getState(Context context, Intent intent) {
        State.USB state = State.USB.DISCONNECTED;

        if(intent.getExtras().getBoolean("connected"))
            state = State.USB.CONNECTED;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_usb;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.usb_connected, State.USB.CONNECTED.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.usb_disconnected, State.USB.DISCONNECTED.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
