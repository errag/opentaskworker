package com.spif.actions;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.spif.models.Action;
import com.spif.models.Parameter;
import com.spif.models.State;
import com.spif.opentaskworker.R;

public class BluetoothAction extends Action {
    public BluetoothAction() {
        super();
    }

    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        // get bluetooth adapter
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean bluetoothEnabled = bluetoothAdapter.isEnabled();

        // get parameters
        boolean turnOn = Boolean.parseBoolean(params[0].getInput());
        boolean turnOff = Boolean.parseBoolean(params[1].getInput());
        boolean turnToggle = Boolean.parseBoolean(params[2].getInput());

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
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.bluetooth_on, State.Bluetooth.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.bluetooth_off, State.Bluetooth.OFF.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.bluetooth_toggle, State.Bluetooth.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
