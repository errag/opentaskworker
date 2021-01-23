package com.errag.opentaskworker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.errag.models.OTWService;
import com.errag.models.TimerObject;
import com.errag.opentaskworker.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent otwIntent = null;
    private OTWService otwService = null;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this.context = this;

            TaskController taskController = TaskController.createInstance(this);
            GuiController.initApp(this, taskController);

            otwService = new OTWService();
            otwIntent = new Intent(getContext(), otwService.getClass());

            if (!OTWService.isRunning(getContext()))
                startService(otwIntent);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("otw.service.restart");
        broadcastIntent.setClass(this, RestartBroadcastReceiver.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    private Context getContext() {
        return this.context;
    }
}