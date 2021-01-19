package com.spif.actions;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.spif.models.Action;
import com.spif.models.Parameter;
import com.spif.models.State;
import com.spif.opentaskworker.R;

public class WifiAction extends Action {
    public WifiAction() {
        super();
    }

    @Override
    public boolean exec(Context context, Parameter[] params) {
        // get wifi adapter
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        boolean wifiEnabled = wifiManager.isWifiEnabled();

        // get parameter
        boolean turnOn = Boolean.parseBoolean(params[0].getInput());
        boolean turnOff = Boolean.parseBoolean(params[1].getInput());
        boolean turnToggle = Boolean.parseBoolean(params[2].getInput());

        if(turnOn || (turnToggle && !wifiEnabled))
            wifiManager.setWifiEnabled(true);
        else if(turnOff || (turnToggle && wifiEnabled))
            wifiManager.setWifiEnabled(false);

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_wifi;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.wifi_on, State.Wifi.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.wifi_off, State.Wifi.OFF.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.wifi_toggle, State.Wifi.TOGGLE.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
