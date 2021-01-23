package com.errag.actions;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class FlashAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        boolean flashOn = getACBoolean(State.Flash.ON.toString(), params);
        boolean flashOff = getACBoolean(State.Flash.OFF.toString(), params);

        CameraManager cameraManager = (CameraManager)context.getApplicationContext().getSystemService(Context.CAMERA_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (String id : cameraManager.getCameraIdList()) {
                if (cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    cameraManager.setTorchMode(id, flashOn && !flashOff);
            }
        }

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_flash;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[]{
                new Parameter(R.string.flash_on, State.Flash.ON.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.flash_off, State.Flash.OFF.toString(), Parameter.Type.RADIO)
        };
    }
}
