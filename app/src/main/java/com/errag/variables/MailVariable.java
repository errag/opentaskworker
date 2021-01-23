package com.errag.variables;

import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

public class MailVariable extends Variable {

    @Override
    public int getImage() {
        return R.drawable.img_mail;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.email_server, EMAIL.SERVER.toString(), Parameter.Type.STRING),
                new Parameter(R.string.email_port, EMAIL.PORT.toString(), Parameter.Type.STRING),
                new Parameter(R.string.email_auth, EMAIL.USE_AUTH.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.email_username, EMAIL.USERNAME.toString(), Parameter.Type.STRING),
                new Parameter(R.string.email_password, EMAIL.PASSWORD.toString(), Parameter.Type.PASSWORD)
        };
    }
}
