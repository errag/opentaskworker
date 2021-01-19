package com.errag.opentaskworker;

import android.content.Context;

import com.errag.actions.BluetoothAction;
import com.errag.actions.DelayAction;
import com.errag.actions.WifiAction;
import com.errag.models.Action;
import com.errag.models.SelectionViewItem;
import com.errag.models.Sensor;
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
    private final static String SENSOR_PKG = "com.errag.sensors";
    private final static String ACTION_PKG = "com.errag.actions";

    private Context context = null;
    private ArrayList<Action> actions = new ArrayList<>();
    private ArrayList<Sensor> sensors = new ArrayList<>();

    public TaskController(Context _context) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException {
        this.context = _context;
        this.loadTaskElements();
    }

    private void loadTaskElements() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        DexFile df = new DexFile(this.context.getPackageCodePath());

        for (Enumeration<String> iter = df.entries(); iter.hasMoreElements();) {
            String className = iter.nextElement();

            if(className.startsWith(ACTION_PKG) || className.startsWith(SENSOR_PKG)) {
                Class<?> classObj = Class.forName(className);

                if(className.startsWith(ACTION_PKG))
                    actions.add((Action)classObj.newInstance());
                else
                    sensors.add((Sensor)classObj.newInstance());
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

    //
    public Action[] getActions() {
        return this.actions.toArray(new Action[this.actions.size()]);
    }

    public Action[] getAvailableActions() {
        try {
            DexFile df = new DexFile(context.getPackageCodePath());
            System.out.println("*** " + context.getPackageCodePath());

            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements();) {
                String s = iter.nextElement();

                if(s.contains(".actions."))
                    System.out.println(s);
            }

        } catch(Exception ex) {

        }

        List<Action> availableActions = new ArrayList<>();
        availableActions.add(new BluetoothAction());
        availableActions.add(new DelayAction());
        availableActions.add(new WifiAction());

        return availableActions.toArray(new Action[availableActions.size()]);
    }

    //
    public Sensor[] getSensors() {
        return this.sensors.toArray(new Sensor[this.sensors.size()]);
    }

    public Sensor[] getAvailableSensors() {
        List<Sensor> availableSensors = new ArrayList<>();
        availableSensors.add(new AirplainModeSensor());
        availableSensors.add(new APSensor());
        availableSensors.add(new BatteryLevelSensor());
        availableSensors.add(new BatterySensor());
        availableSensors.add(new BluetoothSensor());
        availableSensors.add(new BootSensor());
        availableSensors.add(new CameraSensor());
        availableSensors.add(new ConnectionSensor());
        availableSensors.add(new GPSSensor());
        availableSensors.add(new InterruptionSensor());
        availableSensors.add(new OrientationSensor());
        availableSensors.add(new OutgoingCallSensor());
        availableSensors.add(new PhoneSensor());
        availableSensors.add(new PowerSaveMoveSensor());
        availableSensors.add(new ScreenOffSensor());
        availableSensors.add(new ScreenOnSensor());
        availableSensors.add(new SMSSensor());
        availableSensors.add(new USBSensor());
        availableSensors.add(new WifiSensor());

        return availableSensors.toArray(new Sensor[availableSensors.size()]);
    }
}
