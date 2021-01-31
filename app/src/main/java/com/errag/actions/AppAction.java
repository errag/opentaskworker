package com.errag.actions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.MainActivity;
import com.errag.opentaskworker.PermissionController;
import com.errag.opentaskworker.R;
import com.errag.opentaskworker.TaskController;

import java.util.List;

public class AppAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        String packageName = getACString(State.APP.START.toString(), params);
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        if(intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }

        return true;
    }

    @Override
    public void askForPermissions(Activity activity) {
        PermissionController.askForOverlay(activity);
    }

    @Override
    public int getImage() {
        return R.drawable.img_app;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        String packages = "";
        Context context = MainActivity.getContext();

        if(context != null) {
            List<ApplicationInfo> apps = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);

            for(ApplicationInfo info : apps) {
                Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(info.packageName);

                if(appIntent != null)
                    packages += info.packageName + ";";
            }
        }

        return new Parameter[] {
                new Parameter(R.string.app_start, State.APP.START.toString(), Parameter.Type.DROPDOWN, packages)
        };
    }
}
