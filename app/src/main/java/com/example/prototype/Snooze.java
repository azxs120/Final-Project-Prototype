package com.example.prototype;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Snooze extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit").
                setSmallIcon(R.drawable.ic_action_email).setContentTitle("נזילה").
                setContentText("יש נזילה במרפסת שירות, נצטרך שבעל מקצוע יבוא לטפל.").
                setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());

    }
}
