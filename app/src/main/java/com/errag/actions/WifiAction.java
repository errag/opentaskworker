package com.errag.actions;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.PermissionController;
import com.errag.opentaskworker.R;

public class WifiAction extends Action {
    public WifiAction() {
        super();
    }

    @Override
    public boolean exec(Context context, Parameter[] params) {
        boolean result = false;

        // get wifi adapter
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        boolean wifiEnabled = wifiManager.isWifiEnabled();

        // get parameter
        boolean turnOn = getACBoolean(State.Wifi.ON.toString(), params);
        boolean turnOff = getACBoolean(State.Wifi.OFF.toString(), params);
        boolean turnToggle = getACBoolean(State.Wifi.TOGGLE.toString(), params);

        // do action
        if(turnOn || (turnToggle && !wifiEnabled))
            result = wifiManager.setWifiEnabled(true);
        else if(turnOff || (turnToggle && wifiEnabled))
            result = wifiManager.setWifiEnabled(false);

        return result;
    }

    @Override
    public void askForPermissions(Activity activity) {
        PermissionController.askForWifi(activity);
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
