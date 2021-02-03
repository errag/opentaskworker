package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class APSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.net.wifi.WIFI_AP_STATE_CHANGED";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        int apState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
        State.AP state = (apState == 13 ? State.AP.OFF : State.AP.OFF);

        return params.testValue(state.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_ap;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.ap_on, State.AP.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.ap_off, State.AP.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
