package com.errag.variables;

import com.errag.models.Parameter;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

public class NumberVariable extends Variable {
    @Override
    public int getImage() {
        return R.drawable.img_number;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.number_input, NUMBER.VALUE.toString(), Parameter.Type.INTEGER)
        };
    }
}
