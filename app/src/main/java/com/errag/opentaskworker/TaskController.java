package com.errag.opentaskworker;

import android.content.Context;

import com.errag.actions.BluetoothAction;
import com.errag.actions.DelayAction;
import com.errag.actions.WifiAction;
import com.errag.models.Action;
import com.errag.models.SelectionViewItem;
import com.errag.models.Sensor;
import com.errag.models.Variable;
import com.errag.sensors.APSensor;
import com.errag.sensors.AirplainModeSensor;
import com.errag.sensors.BatteryLevelSensor;
import com.errag.sensors.BatterySensor;
import com.errag.sensors.BluetoothSensor;
import com.errag.sensors.BootSensor;
import com.errag.sensors.CameraSensor;
import com.errag.sensors.ConnectionSensor;
import com.errag.sensors.GPSSensor;
import com.errag.sensors.InterruptionSensor;
import com.errag.sensors.OrientationSensor;
import com.errag.sensors.OutgoingCallSensor;
import com.errag.sensors.PhoneSensor;
import com.errag.sensors.PowerSaveMoveSensor;
import com.errag.sensors.SMSSensor;
import com.errag.sensors.ScreenOffSensor;
import com.errag.sensors.ScreenOnSensor;
import com.errag.sensors.USBSensor;
import com.errag.sensors.WifiSensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

public class TaskController {
    private final static String SENSOR_PKG      = "com.errag.sensors";
    private final static String ACTION_PKG      = "com.errag.actions";
    private final static String VARIABLE_PKG    = "com.errag.variables";

    private Context context = null;
    private ArrayList<Action> actions = new ArrayList<>();
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<Variable> variables = new ArrayList<>();

    public TaskController(Context _context) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException {
        this.context = _context;
        this.loadTaskElements();
    }

    private void loadTaskElements() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        DexFile df = new DexFile(this.context.getPackageCodePath());

        for (Enumeration<String> iter = df.entries(); iter.hasMoreElements();) {
            String className = iter.nextElement();

            if(className.startsWith(ACTION_PKG) || className.startsWith(SENSOR_PKG) || className.startsWith(VARIABLE_PKG)) {
                Class<?> classObj = Class.forName(className);

                if(className.startsWith(ACTION_PKG))
                    actions.add((Action)classObj.newInstance());
                else if(className.startsWith(SENSOR_PKG))
                    sensors.add((Sensor)classObj.newInstance());
                else
                    variables.add((Variable)classObj.newInstance());
            }
        }
    }

    public Sensor getSensorByAction(String action) {
        Sensor actionSensor = null;

        for(Sensor sensor : sensors) {
            if(sensor.isAction(action))
            {
                actionSensor = sensor;
                break;
            }
        }

        return actionSensor;
    }

    public Action[] getActions() {
        return this.actions.toArray(new Action[this.actions.size()]);
    }

    public Sensor[] getSensors() {
        return this.sensors.toArray(new Sensor[this.sensors.size()]);
    }

    public Variable[] getVariables() {
        return this.variables.toArray(new Variable[this.variables.size()]);
    }
}
