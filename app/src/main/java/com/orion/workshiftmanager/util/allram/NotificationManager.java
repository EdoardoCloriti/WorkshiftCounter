package com.orion.workshiftmanager.util.allram;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by Edoardo on 25/02/2016.
 */
public class NotificationManager extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm =(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl= pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"WorkShiftManager");
        wl.acquire();
        //TODO: PUT here your code
        Toast.makeText(context,"TEST!!!",Toast.LENGTH_LONG).show();
        wl.release();
    }

    public void setNotify(Context context)
    {
        AlarmManager am=(AlarmManager)  context.getSystemService(Context.ALARM_SERVICE);
        Intent i= new Intent(context,NotificationManager.class);
        PendingIntent pi= PendingIntent.getBroadcast(context,0,i,0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pi); // Millisec * Second * Minute
    }

    public void CancelNotify(Context context)
    {
        Intent intent = new Intent(context, NotificationManager.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
