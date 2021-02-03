package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class BatteryLevelSensor extends Sensor {

    @Override
    public String getActionName() {
        return Intent.ACTION_BATTERY_CHANGED;
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        boolean result = false;
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float)scale;
        String batteryLevel = String.valueOf(batteryPct);

        result = params.testValue(State.BatteryLevel.CHANGED.toString(), true);
        result = params.testValue(State.BatteryLevel.VALUE.toString(), batteryLevel);

        return result;
    }

    @Override
    public int getImage() {
        return R.drawable.img_battery_level;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.batter_level_changed, State.BatteryLevel.CHANGED.toString(), Parameter.Type.INTEGER)
        };
    }
}
