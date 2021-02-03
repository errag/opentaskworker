package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class SimCardSensor extends Sensor {
    private static final String EXTRA_SIM_STATE = "ss";

    @Override
    public String getActionName() {
        return "android.intent.action.SIM_STATE_CHANGED";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        String simState = intent.getExtras().getString(EXTRA_SIM_STATE);

        return params.testValue(State.SimCard.class.getName() + "." + simState, true);
    }

    @Override
    public int getImage() {
        return R.drawable.img_sim;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.sim_locked, State.SimCard.LOCKED.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.sim_imsi, State.SimCard.IMSI.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.sim_loaded, State.SimCard.LOADED.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.sim_ready, State.SimCard.READY.toString(), Parameter.Type.RADIO),
        };
    }
}
