package com.errag.actions;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class NotifyAction extends Action {
    private final static String channelID = "OTW_NotifyAction";
    private final static String channelName = "OpenTaskWorker";
    private final static String channelDesc = "OpenTaskWorker automation Notifications";
    private final static String ticker = "Open Task Worker";

    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        String title = getACString(State.Notify.TITLE.toString(), params);
        String text = getACString(State.Notify.TEXT.toString(), params);
        Boolean vibration = getACBoolean(State.Notify.VIBRATE.toString(), params);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription(channelDesc);

            if(vibration) {
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
            }

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notify_icon)
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(text);

        notificationManager.notify(1, notificationBuilder.build());

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_notify;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.notify_title, State.Notify.TITLE.toString(), Parameter.Type.STRING),
                new Parameter(R.string.notify_text, State.Notify.TEXT.toString(), Parameter.Type.STRING),
                new Parameter(R.string.notify_vibrate, State.Notify.VIBRATE.toString(), Parameter.Type.BOOLEAN),
        };
    }
}
