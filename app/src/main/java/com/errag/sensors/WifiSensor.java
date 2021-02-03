package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class WifiSensor extends Sensor {
    @Override
    public String getActionName() {
        return WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION;
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        boolean result = false;
        Boolean wifiState = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);
        State.Wifi state = (wifiState ? State.Wifi.ON : State.Wifi.OFF);
        result = params.testValue(state.toString(), true);

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        if(info != null && info.isConnected()) {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            result = params.testValue(State.Wifi.SSID.toString(), wifiInfo.getSSID());
        }

        return result;
    }

    @Override
    public int getImage() {
        return R.drawable.img_wifi;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.wifi_on, State.Wifi.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.wifi_off, State.Wifi.OFF.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.wifi_ssid, State.Wifi.SSID.toString(), Parameter.Type.STRING)
        };
    }
}
