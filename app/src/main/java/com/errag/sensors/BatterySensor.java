package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class BatterySensor extends Sensor {
    @Override
    public String getActionName() {
        return Intent.ACTION_BATTERY_CHANGED;
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        State.Battery state = State.Battery.NOT_CHARGING;
        int batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        if(batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING ||  batteryStatus == BatteryManager.BATTERY_STATUS_FULL)
            state = State.Battery.CHARGING;

        return params.testValue(state.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_battery;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.batter_charging, State.Battery.CHARGING.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.batter_discharching, State.Battery.NOT_CHARGING.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
