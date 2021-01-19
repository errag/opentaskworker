package com.spif.sensors;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.spif.models.Parameter;
import com.spif.models.Sensor;
import com.spif.models.State;
import com.spif.opentaskworker.R;

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
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.gps_on, State.GPS.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.gps_off, State.GPS.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
