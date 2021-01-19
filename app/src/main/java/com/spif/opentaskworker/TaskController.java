package com.spif.opentaskworker;

import com.spif.actions.BluetoothAction;
import com.spif.actions.DelayAction;
import com.spif.actions.WifiAction;
import com.spif.models.Action;
import com.spif.models.Sensor;
import com.spif.sensors.APSensor;
import com.spif.sensors.AirplainModeSensor;
import com.spif.sensors.BatteryLevelSensor;
import com.spif.sensors.BatterySensor;
import com.spif.sensors.BluetoothSensor;
import com.spif.sensors.BootSensor;
import com.spif.sensors.CameraSensor;
import com.spif.sensors.ConnectionSensor;
import com.spif.sensors.GPSSensor;
import com.spif.sensors.InterruptionSensor;
import com.spif.sensors.OrientationSensor;
import com.spif.sensors.OutgoingCallSensor;
import com.spif.sensors.PhoneSensor;
import com.spif.sensors.PowerSaveMoveSensor;
import com.spif.sensors.SMSSensor;
import com.spif.sensors.ScreenOffSensor;
import com.spif.sensors.ScreenOnSensor;
import com.spif.sensors.USBSensor;
import com.spif.sensors.WifiSensor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskController {
    private final static String SENSOR_PKG = "com.spif.sensors";

    private ClassLoader classLoader = null;
    private ArrayList<Action> actions = new ArrayList<>();
    private ArrayList<Sensor> sensors = new ArrayList<>();

    public TaskController(ClassLoader _classLoader)
    {
        this.classLoader = _classLoader;
        this.loadSensorsFromPackage();
        this.loadActionsFromPackage();
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
    public ArrayList<Action> getActions() {
        return this.actions;
    }

    private void loadActionsFromPackage()  {
        actions.addAll(Arrays.asList(getAvailableActions()));
    }

    public Action[] getAvailableActions() {
        List<Action> availableActions = new ArrayList<>();
        availableActions.add(new BluetoothAction());
        availableActions.add(new DelayAction());
        availableActions.add(new WifiAction());

        return availableActions.toArray(new Action[availableActions.size()]);
    }

    //
    public ArrayList<Sensor> getSensors() {
        return this.sensors;
    }

    private void loadSensorsFromPackage() {
        sensors.addAll(Arrays.asList(getAvailableSensors()));
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
