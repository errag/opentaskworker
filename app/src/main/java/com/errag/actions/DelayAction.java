package com.errag.actions;

import android.content.Context;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class DelayAction extends Action {
    public DelayAction() {
        super();
    }

    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        Integer sleepTime = getACInteger(State.Delay.MS.toString(), params);
        Thread.sleep(sleepTime);

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_delay;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.delay_time, State.Delay.MS.toString(), Parameter.Type.INTEGER)
        };
    }
}
