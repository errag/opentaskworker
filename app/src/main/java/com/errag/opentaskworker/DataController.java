package com.errag.opentaskworker;

import android.content.Context;
import android.content.SharedPreferences;

import com.errag.models.Action;
import com.errag.models.GsonAdapter;
import com.errag.models.Sensor;
import com.errag.models.Settings;
import com.errag.models.Task;
import com.errag.models.Variable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataController {
    private final static String SAVE_NAME = "OTW-STORAGE2";
    private final static String SAVE_TASKS = "TASKS";
    private final static String SAVE_VARIABLES = "VARIABLES";

    private Context context = null;

    public DataController(Context _context) {
        this.context = _context;
    }

    public void saveTasks(List<Task> tasks) {
        SharedPreferences settings = context.getSharedPreferences(SAVE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = getGson();
        editor.putString(SAVE_TASKS, gson.toJson(tasks));
        editor.apply();
    }

    public List<Task> loadTasks() {
        List<Task> tasks = null;

        try {
            SharedPreferences settings = context.getSharedPreferences(SAVE_NAME, Context.MODE_PRIVATE);
            Gson gson = getGson();
            tasks = gson.fromJson(settings.getString(SAVE_TASKS, ""), new TypeToken<List<Task>>() {}.getType());

            if(tasks == null)
                tasks = new ArrayList<>();
        } catch(Exception ex) {
            ex.printStackTrace();
            tasks = new ArrayList<>();
        }

        return tasks;
    }

    public void saveSettings(Settings appSettings) {
        SharedPreferences settings = context.getSharedPreferences(SAVE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = getGson();
        editor.putString(SAVE_VARIABLES, gson.toJson(appSettings));
        editor.apply();
    }

    public Settings loadSettings() {
        Settings appSettings = null;

        try {
            SharedPreferences settings = context.getSharedPreferences(SAVE_NAME, Context.MODE_PRIVATE);
            Gson gson = getGson();
            appSettings = gson.fromJson(settings.getString(SAVE_VARIABLES, ""), new TypeToken<Settings>() {}.getType());

            if(appSettings == null)
                appSettings = new Settings();
        } catch(Exception ex) {
            ex.printStackTrace();
            appSettings = new Settings();
        }

        return appSettings;
    }

    private Gson getGson() {
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Action.class, new GsonAdapter<Action>());
        gsonBilder.registerTypeAdapter(Sensor.class, new GsonAdapter<Sensor>());
        gsonBilder.registerTypeAdapter(Variable.class, new GsonAdapter<Variable>());
        gsonBilder.setPrettyPrinting();

        return gsonBilder.create();
    }
}
