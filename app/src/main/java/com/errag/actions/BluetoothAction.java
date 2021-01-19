package com.errag.actions;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

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
        boolean turnOn = getACBoolean(State.Bluetooth.ON.toString(), params);
        boolean turnOff = getACBoolean(State.Bluetooth.OFF.toString(), params);
        boolean turnToggle = getACBoolean(State.Bluetooth.TOGGLE.toString(), params);

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
