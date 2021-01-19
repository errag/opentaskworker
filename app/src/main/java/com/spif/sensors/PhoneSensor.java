package com.spif.sensors;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.spif.models.Parameter;
import com.spif.models.Sensor;
import com.spif.models.State;
import com.spif.opentaskworker.R;

public class PhoneSensor extends Sensor {

    @Override
    public String getActionName() {
        return "android.intent.action.PHONE_STATE";
    }

    @Override
    public String getState(Context context, Intent intent) {
        State.Phone state = State.Phone.UNKNOWN;
        String phoneState = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);

        if(phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE))
            state = State.Phone.CALL_STATE_IDLE;
        else if(phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
            state = State.Phone.CALL_STATE_OFFHOOK;
        else if(phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING))
            state = State.Phone.CALL_STATE_RINGING;

        return state.toString();
    }

    @Override
    public String getSensorValue(Context context, Intent intent) {
        return intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
    }

    @Override
    public int getImage() {
        return R.drawable.img_phone;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.phone_idle, State.Phone.CALL_STATE_IDLE.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.phone_ringing, State.Phone.CALL_STATE_RINGING.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.phone_offhock, State.Phone.CALL_STATE_OFFHOOK.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.phone_number, null, Parameter.Type.STRING)
        };
    }
}
