package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.net.Proxy;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class ProxySensor extends Sensor {
    @Override
    public String getActionName() {
        return Proxy.PROXY_CHANGE_ACTION;
    }

    @Override
    public String getState(Context context, Intent intent) {
        return State.Proxy.CHANGED.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_proxy;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.proxy_changed, State.Proxy.CHANGED.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
