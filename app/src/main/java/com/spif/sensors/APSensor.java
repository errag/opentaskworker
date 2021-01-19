package com.spif.sensors;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.spif.models.Parameter;
import com.spif.models.Sensor;
import com.spif.models.State;
import com.spif.opentaskworker.R;

public class APSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.net.wifi.WIFI_AP_STATE_CHANGED";
    }

    @Override
    public String getState(Context context, Intent intent) {
        State.AP state = State.AP.OFF;
        int apState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

        if(apState == 13)
            state = State.AP.ON;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_ap;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.ap_on, State.AP.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.ap_off, State.AP.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
