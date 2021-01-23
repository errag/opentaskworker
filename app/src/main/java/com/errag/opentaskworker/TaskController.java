package com.errag.opentaskworker;

import android.content.Context;
import android.content.Intent;

import com.errag.actions.BluetoothAction;
import com.errag.actions.DelayAction;
import com.errag.actions.WifiAction;
import com.errag.models.Action;
import com.errag.models.OTWService;
import com.errag.models.SelectionViewItem;
import com.errag.models.Sensor;
import com.errag.models.Settings;
import com.errag.models.Task;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

public class TaskController {
    private final static String SENSOR_PKG      = "com.errag.sensors";
    private final static String ACTION_PKG      = "com.errag.actions";
    private final static String VARIABLE_PKG    = "com.errag.variables";

    private Context context = null;
    private DataController dataController = null;
    private List<Task> tasks = null;
    private Settings settings = null;

    private static TaskController taskController = null;

    private TaskController(Context _context) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException {
        this.context = _context;
        this.dataController = new DataController(this.context);
        this.tasks = dataController.loadTasks();
        this.settings = dataController.loadSettings();
    }

    public static TaskController createInstance(Context _context) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException {
        if(taskController == null)
            taskController = new TaskController(_context);

        return taskController;
    }

    public static TaskController getInstance() {
        return taskController;
    }

    /*** TASK OPERATIONS ***/

    public boolean addTask(String name, List<Sensor> sensors, List<Action> actions) throws Exception {
        this.tasks.add(new Task(name, sensors, actions));
        this.saveTasks();

        return true;
    }

    public boolean editTask(Task task, List<Sensor> _sensors, List<Action> _actions) {
        task.setSensors(_sensors);
        task.setActions(_actions);
        this.saveTasks();

        return true;
    }

    public Task getTask(String _name) {
        Task task = null;

        for(Task _task : this.tasks) {
            if(_task.getName().equals(_name))
                task = _task;
        }

        return task;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public boolean removeTask(Task task) {
        boolean contains = this.tasks.contains(task);

        if(contains) {
            this.tasks.remove(task);
            saveTasks();
        }

        return contains;
    }

    /*** SETTINGS OPERATIONS ***/

    public boolean addVariable(Variable variable) {
        this.settings.addVariable(variable);
        this.saveSettings();

        return true;
    }

    public boolean containsVariable(Variable variable) {
        boolean contains = this.settings.getVariables().contains(variable);

        if(contains)
            this.saveSettings();

        return contains;
    }

    public boolean removeVariable(Variable variable) {
        this.settings.removeVariable(variable);
        this.saveSettings();

        return true;
    }

    public Variable getVariableByName(String _name) {
        return settings.getVariableByName(_name);
    }

    /*** SERVICE FUNCTIONS ***/

    public void setService(boolean flag) {
        boolean isRunning = this.isServiceRunning();

        if(flag && !isRunning)
            startService();
        else if(!flag && isRunning)
            stopService();
    }

    public boolean isServiceRunning() {
        return OTWService.isRunning(this.context);
    }

    private void startService() {
        Intent serviceIntent = new Intent(this.context, OTWService.class);
        this.context.startService(serviceIntent);
    }

    private void stopService() {
        this.context.stopService(new Intent(this.context, OTWService.class));
    }

    /*** FILE I/O FUNCTIONS***/

    public void saveTasks() {
        this.dataController.saveTasks(this.tasks);
    }

    public void saveSettings() {
        this.dataController.saveSettings(this.settings);
    }

    /*** REFLECTIONS - GET OTW ELEMENTS ***/

    public List<Variable> getUsedVariables() {
        return this.settings.getVariables();
    }

    public Action[] getAvailableActions() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException  {
        Object[] elements = loadTaskElements(ACTION_PKG);

        return Arrays.copyOf(elements, elements.length, Action[].class);
    }

    public Sensor[] getAvailableSensors() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException  {
        Object[] elements = loadTaskElements(SENSOR_PKG);

        return Arrays.copyOf(elements, elements.length, Sensor[].class);
    }

    public Variable[] getAvailableVariables() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException  {
        Object[] elements = loadTaskElements(VARIABLE_PKG);

        return Arrays.copyOf(elements, elements.length, Variable[].class);
    }

    private Object[] loadTaskElements(String pkg) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        List<SelectionViewItem> items = new ArrayList<>();
        DexFile df = new DexFile(this.context.getPackageCodePath());

        for (Enumeration<String> iter = df.entries(); iter.hasMoreElements();) {
            String className = iter.nextElement();

            if(className.startsWith(pkg)) {
                Class<?> classObj = Class.forName(className);

                if(className.startsWith(pkg))
                    items.add((SelectionViewItem) classObj.newInstance());
            }
        }

        return items.toArray();
    }

}
