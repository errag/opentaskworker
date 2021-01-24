package com.errag.opentaskworker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RestartBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("RESTART = " + OTWService.RESTART);
        if(OTWService.RESTART) {
        /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            context.startForegroundService(new Intent(context, OTWService.class));
        else*/
            context.startService(new Intent(context, OTWService.class));
        }
    }
}
