package com.errag.sensors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.PermissionController;
import com.errag.opentaskworker.R;

public class PhoneSensor extends Sensor {

    @Override
    public String getActionName() {
        return "android.intent.action.PHONE_STATE";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        boolean result = false;
        State.Phone state = State.Phone.UNKNOWN;
        String phoneState = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if(phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE))
            state = State.Phone.CALL_STATE_IDLE;
        else if(phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
            state = State.Phone.CALL_STATE_OFFHOOK;
        else if(phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING))
            state = State.Phone.CALL_STATE_RINGING;

        result = params.testValue(state.toString(), true);
        result = params.testValue(State.Phone.NUMBER.toString(), number);

        return result;
    }

    @Override
    public void askForPermissions(Activity activity) {
        PermissionController.askForCamera(activity);
    }

    @Override
    public int getImage() {
        return R.drawable.img_phone;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.phone_idle, State.Phone.CALL_STATE_IDLE.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.phone_ringing, State.Phone.CALL_STATE_RINGING.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.phone_offhock, State.Phone.CALL_STATE_OFFHOOK.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.phone_number, State.Phone.NUMBER.toString(), Parameter.Type.STRING)
        };
    }
}
