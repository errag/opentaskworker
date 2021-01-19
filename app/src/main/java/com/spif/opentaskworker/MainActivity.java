package com.spif.opentaskworker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.spif.models.TimerObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<TimerObject> list = new ArrayList<TimerObject>();
        list.add(TimerObject.getAlarmObjectInterval(1));

        TaskController taskController = new TaskController(this.getClassLoader());
        EventController eventController = new EventController(this, taskController);
        GuiController guiController = new GuiController(this, taskController);
    }
}