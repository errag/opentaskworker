package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class WifiSensor extends Sensor {
    @Override
    public String getActionName() {
        return WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION;
    }

    @Override
    public String getState(Context context, Intent intent) {
        State.Wifi state = State.Wifi.OFF;

        if(intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false))
            state = State.Wifi.ON;

        return state.toString();
    }

    @Override
    protected String getSensorValue(Context context, Intent intent) {
        String ssid = null;
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        if(info != null && info.isConnected()) {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ssid = wifiInfo.getSSID();
        }

        return ssid;
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
                new Parameter(R.string.wifi_ssid, null, Parameter.Type.STRING)
        };
    }
}
