package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.spif.opentaskworker.R;

public class PowerSaveMoveSensor extends Sensor {

    @Override
    public String getActionName() {
        return PowerManager.ACTION_POWER_SAVE_MODE_CHANGED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public String getState(Context context, Intent intent) {
        State.PowerSaveMove state = State.PowerSaveMove.OFF;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        if(pm.isPowerSaveMode())
            state = State.PowerSaveMove.ON;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_powersavemode;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.powersavemode_on, State.PowerSaveMove.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.powersavemode_off, State.PowerSaveMove.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
