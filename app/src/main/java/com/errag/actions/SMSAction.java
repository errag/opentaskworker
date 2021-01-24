package com.errag.actions;

import android.app.Activity;
import android.content.Context;
import android.telephony.SmsManager;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.PermissionController;
import com.errag.opentaskworker.R;

public class SMSAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        String phoneNumber = getACString(State.SMS.SEND_TO.toString(), params);
        String smsMessage = getACString(State.SMS.MESSAGE.toString(), params);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, smsMessage, null, null);

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_sms;
    }

    @Override
    public void askForPermissions(Activity activity) {
        PermissionController.askForSMSSend(activity);
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.sms_to_number, State.SMS.SEND_TO.toString(), Parameter.Type.STRING),
                new Parameter(R.string.sms_message, State.SMS.MESSAGE.toString(), Parameter.Type.STRING)
        };
    }
}
