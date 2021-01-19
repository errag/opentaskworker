package com.errag.opentaskworker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.errag.models.TimerObject;
import com.errag.opentaskworker.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ArrayList<TimerObject> list = new ArrayList<TimerObject>();
            list.add(TimerObject.getAlarmObjectInterval(1));

            TaskController taskController = new TaskController(this);
            EventController eventController = new EventController(this, taskController);
            GuiController guiController = new GuiController(this, taskController);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}