package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class PowerSaveMoveSensor extends Sensor {

    @Override
    public String getActionName() {
        return PowerManager.ACTION_POWER_SAVE_MODE_CHANGED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        State.PowerSaveMove state = (pm.isPowerSaveMode() ? State.PowerSaveMove.ON : State.PowerSaveMove.OFF);

        return params.testValue(state.toString(), true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_powersavemode;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.powersavemode_on, State.PowerSaveMove.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.powersavemode_off, State.PowerSaveMove.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
