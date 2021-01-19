package com.errag.actions;

import android.content.Context;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.spif.opentaskworker.R;

public class DelayAction extends Action {
    public DelayAction() {
        super();
    }

    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        Integer sleepTime = Integer.parseInt(params[0].getInput());
        Thread.sleep(sleepTime);

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_delay;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.delay_time, null, Parameter.Type.INTEGER)
        };
    }
}
