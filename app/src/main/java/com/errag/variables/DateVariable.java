package com.errag.variables;

import com.errag.models.Parameter;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

public class DateVariable extends Variable {
    @Override
    public int getImage() {
        return R.drawable.img_calendar;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.date_value, DATE.VALUE.toString(), Parameter.Type.DATE)
        };
    }
}
