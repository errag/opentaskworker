package com.errag.opentaskworker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionController {
    private PermissionController() {

    }

    public static void askForBatteryStats(Activity activity) {
        askForPermission(activity, new String[] {Manifest.permission.BATTERY_STATS});
    }

    public static void askForBluetooth(Activity activity) {
        askForPermission(activity, new String[] {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH_PRIVILEGED});
    }

    public static void askForCamera(Activity activity) {
        askForPermission(activity, new String[] {Manifest.permission.CAMERA});
    }

    public static void askForFileIO(Activity activity) {
        askForPermission(activity, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    public static void askForPhone(Activity activity) {
        askForPermission(activity, new String[] {Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS});
    }

    public static void askForSMSReceive(Activity activity) {
        askForPermission(activity, new String[] {Manifest.permission.RECEIVE_SMS});
    }

    public static void askForSMSSend(Activity activity) {
        askForPermission(activity, new String[] {Manifest.permission.SEND_SMS});
    }

    public static void askForWriteSettings(Activity activity) {
        if(!Settings.System.canWrite(activity))
        {
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            activity.startActivity(intent);
        }
    }

    public static void askForVibrate(Activity activity) {
        askForPermission(activity, new String[] {Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.VIBRATE});
    }

    public static void askForWifi(Activity activity) {
        askForPermission(activity, new String[] {
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE}
        );
    }

    private static void askForPermission(Activity activity, String[] permission) {
        boolean hasPermission = true;

        for(String perm : permission) {
            if(ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED)
                hasPermission = false;
        }

        if(!hasPermission)
            ActivityCompat.requestPermissions(activity, permission, 1);

        System.out.println("Permission: " + hasPermission);
    }
}
