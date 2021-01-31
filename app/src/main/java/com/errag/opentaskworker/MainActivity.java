package com.errag.opentaskworker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.errag.gui.GuiAction;

public class MainActivity extends AppCompatActivity {

    private static Intent otwIntent = null;
    private static OTWService otwService = null;
    private static Context context = null;

    public final static int REQUEST_DIRECTORY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            context = this;

            TaskController taskController = TaskController.createInstance(this);
            GuiController.initApp(this, taskController);

            System.out.println(">>> " + isServiceRunning());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("otw.service.restart");
        broadcastIntent.setClass(this, RestartBroadcastReceiver.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String message = null;

        if (requestCode == REQUEST_DIRECTORY) {
            if(resultCode == Activity.RESULT_OK) {
                message = data.getDataString();

                if(message.contains("%3A"))
                    message = message.split("%3A")[1].replaceAll("%2F", "/");
            }
        }

        if(message != null)
            GuiController.getInstance().sendGuiAction(GuiAction.AC.ACTIVITY_RESULT, GuiAction.AC.PARAMETER_DIALOG, null, null, message);
    }

    /*
     *      STATIC SERVICE FUNCTIONS
     */

    public static Context getContext() {
        return context;
    }

    public static void startService() {
        otwService = new OTWService();
        otwIntent = new Intent(getContext(), otwService.getClass());

        if (!isServiceRunning()) {
            OTWService.RESTART = true;
            getContext().startService(otwIntent);
        }
    }

    public static void stopService() {
        if (isServiceRunning()) {
            OTWService.RESTART = false;
            getContext().stopService(new Intent(getContext(), OTWService.class));
        }
    }

    public static boolean isServiceRunning() {
        Class<?> serviceClass = OTWService.class;
        Context context = getContext();

        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName()))
                return true;
        }

        return false;
    }
}