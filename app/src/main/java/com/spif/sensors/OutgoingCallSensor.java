package com.spif.sensors;

import android.content.Context;
import android.content.Intent;

import com.spif.models.Parameter;
import com.spif.models.Sensor;
import com.spif.models.State;
import com.spif.opentaskworker.R;

public class OutgoingCallSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.intent.action.NEW_OUTGOING_CALL";
    }

    @Override
    public String getState(Context context, Intent intent) {
        return State.OutgoingCall.ON.toString();
    }

    @Override
    public String getSensorValue(Context context, Intent intent) {
        return intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
    }

    @Override
    public int getImage() {
        return R.drawable.img_calling;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.outgoingcall_started, State.OutgoingCall.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.phone_number, null, Parameter.Type.STRING)
        };
    }
}
