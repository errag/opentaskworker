package com.errag.variables;

import com.errag.models.Parameter;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

public class TimeVariable extends Variable {
    @Override
    public int getImage() {
        return R.drawable.img_clock;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.time_value, TIME.VALUE.toString(), Parameter.Type.TIME)
        };
    }
}
