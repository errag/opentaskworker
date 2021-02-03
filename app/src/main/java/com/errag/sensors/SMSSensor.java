package com.errag.sensors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class SMSSensor extends Sensor {
    @Override
    public String getActionName() {
        return "android.provider.Telephony.SMS_RECEIVED";
    }

    @Override
    public boolean getStateFromIntent(Context context, Intent intent, ParameterContainer params) {
        boolean result = params.testValue(State.SMS.RECEIVED.toString(), true);

        // check for number
        Bundle bundle = intent.getExtras();
        String senderNumber = null;

        if (bundle != null && bundle.containsKey("pdus")) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[0]);
            senderNumber = sms.getOriginatingAddress();

            result = params.testValue(State.SMS.NUMBER.toString(), senderNumber);
        }

        return result;
    }

    @Override
    public int getImage() {
        return R.drawable.img_sms;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.sms_received, State.SMS.RECEIVED.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.sms_number, State.SMS.NUMBER.toString(), Parameter.Type.STRING)
        };
    }
}
