package com.errag.actions;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class ScreenAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        boolean screenOn = getACBoolean(State.ScreenOn.ON.toString(), params);
        boolean screenOff = getACBoolean(State.ScreenOff.OFF.toString(), params);

        if(screenOn) {
            final Window window = ((Activity)context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        } else if(screenOff) {
            DevicePolicyManager manager = ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE));
            manager.lockNow();
        }

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_screenoff;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[]{
                new Parameter(R.string.screen_on, State.ScreenOn.ON.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.screen_off, State.ScreenOff.OFF.toString(), Parameter.Type.RADIO)
        };
    }
}
