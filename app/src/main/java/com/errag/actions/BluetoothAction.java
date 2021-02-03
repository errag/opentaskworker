package com.errag.actions;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class BluetoothAction extends Action {
    public BluetoothAction() {
        super();
    }

    @Override
    public boolean exec(Context context, ParameterContainer params) throws Exception {
        // get bluetooth adapter
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean bluetoothEnabled = bluetoothAdapter.isEnabled();

        // get parameters
        boolean turnOn = params.getBoolean(State.Bluetooth.ON.toString());
        boolean turnOff = params.getBoolean(State.Bluetooth.OFF.toString());
        boolean turnToggle = params.getBoolean(State.Bluetooth.TOGGLE.toString());

        // do action
        if(turnOn || (turnToggle && !bluetoothEnabled))
            bluetoothAdapter.enable();
        else if(turnOff || (turnToggle && bluetoothEnabled))
            bluetoothAdapter.disable();

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_bluetooth;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.bluetooth_on, State.Bluetooth.ON.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.bluetooth_off, State.Bluetooth.OFF.toString(), Parameter.Type.RADIO),
                new Parameter(R.string.bluetooth_toggle, State.Bluetooth.OFF.toString(), Parameter.Type.RADIO)
        };
    }
}
