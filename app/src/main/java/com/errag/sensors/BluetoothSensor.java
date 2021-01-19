package com.errag.sensors;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class BluetoothSensor extends Sensor
{
    @Override
    public String getActionName() {
        return BluetoothAdapter.ACTION_STATE_CHANGED;
    }

    @Override
    public String getState(Context context, Intent intent) {
        State.Bluetooth state = State.Bluetooth.UNKNOWN;
        int bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

        if(bluetoothState == BluetoothAdapter.STATE_OFF || bluetoothState == BluetoothAdapter.STATE_TURNING_OFF)
            state = State.Bluetooth.OFF;

        if(bluetoothState == BluetoothAdapter.STATE_ON || bluetoothState == BluetoothAdapter.STATE_TURNING_ON)
            state = State.Bluetooth.ON;

        return state.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_bluetooth;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.bluetooth_on, State.Bluetooth.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.bluetooth_off, State.Bluetooth.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
