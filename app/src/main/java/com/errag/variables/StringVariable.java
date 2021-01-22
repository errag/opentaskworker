package com.errag.variables;

import com.errag.models.Parameter;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

public class StringVariable extends Variable {

    @Override
    public int getImage() {
        return R.drawable.img_string;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.string_input, STRING.VALUE.toString(), Parameter.Type.STRING)
        };
    }
}
