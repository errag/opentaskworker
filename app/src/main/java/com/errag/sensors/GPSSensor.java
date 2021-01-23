package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class GPSSensor extends Sensor {
    @Override
    public String getActionName() {
        return LocationManager.PROVIDERS_CHANGED_ACTION;
    }

    @Override
    public String getState(Context context, Intent intent) {
        State.GPS state = State.GPS.OFF;

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isGpsEnabled || isNetworkEnabled)
            state = State.GPS.ON;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_gps;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.gps_on, State.GPS.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.gps_off, State.GPS.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
