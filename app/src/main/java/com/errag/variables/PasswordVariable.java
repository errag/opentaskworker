package com.errag.variables;

import com.errag.models.Parameter;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

public class PasswordVariable extends Variable {
    @Override
    public int getImage() {
        return R.drawable.img_password;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.password_value, PASSWORD.VALUE.toString(), Parameter.Type.PASSWORD)
        };
    }
}
