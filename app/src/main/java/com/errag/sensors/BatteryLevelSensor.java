package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class BatteryLevelSensor extends Sensor {

    @Override
    public String getActionName() {
        return Intent.ACTION_BATTERY_CHANGED;
    }

    @Override
    public String getState(Context context, Intent intent) {
        return State.BatteryLevel.CHANGED.toString();
    }

    @Override
    public String getSensorValue(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float)scale;

        return String.valueOf(batteryPct);
    }

    @Override
    public int getImage() {
        return R.drawable.img_battery_level;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.batter_level_changed, State.BatteryLevel.CHANGED.toString(), Parameter.Type.INTEGER)
        };
    }
}
