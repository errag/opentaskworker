package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class OutgoingCallSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.intent.action.NEW_OUTGOING_CALL";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        boolean result = false;
        String number = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");;
        result = params.testValue(State.OutgoingCall.ON.toString(), true);
        result = params.testValue(State.OutgoingCall.NUMBER.toString(), number);

        return result;
    }

    @Override
    public int getImage() {
        return R.drawable.img_calling;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.outgoingcall_started, State.OutgoingCall.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.phone_number, State.OutgoingCall.NUMBER.toString(), Parameter.Type.STRING)
        };
    }
}
