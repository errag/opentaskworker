package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class ConnectionSensor extends Sensor {
    @Override
    public String getActionName() {
        return ConnectivityManager.CONNECTIVITY_ACTION;
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        State.Connection state = State.Connection.NOT_CONNECTED;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                state = State.Connection.WIFI;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                state = State.Connection.MOBILE_DATA;
        }

        return params.testValue(state.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_connection;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.connection_wifi, State.Connection.WIFI.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.connection_mobile_data, State.Connection.MOBILE_DATA.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.connection_disconnected, State.Connection.NOT_CONNECTED.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
