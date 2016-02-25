package com.orion.workshiftmanager.util.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.util.Turn;
import com.orion.workshiftmanager.util.db.AccessToDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Edoardo on 22/02/2016.
 */
public class WorkShiftCounterNotificationService extends WorkShiftCounterService {

    private NotificationManager manager = null;

    public WorkShiftCounterNotificationService() {
        super();
        setHourToNotify(1);

    }

    public void createNotify(String turnoInizio, String turnoFine, Context context) {
        this.manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Il tuo turno sta per cominciare");
        builder.setContentText("Il tuo Turno comincerà alle: " + turnoInizio + " e finirà alle: " + turnoFine);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setSound(sound);

        manager.notify(0,builder.build());
    }

    @Override
    public void specializedNotify() {
        String HourPattern = PATTERN + "hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        Date inizioTurnoM;
        Date inizioTurnoP;
        Calendar calendar;
        AccessToDB db = new AccessToDB();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        String today = sdf.format(calendar.getTime());
        if (db.existTurn(today, context) != 0) {
            Turn turn = db.getTurnBySelectedDay(today, context);
            String inizioM = turn.getInizioMattina();
            String inizioP = turn.getInizioPomeriggio();
            try {
                inizioTurnoM = sdf.parse(today + inizioM);
                inizioTurnoP = sdf.parse(today + inizioP);
            } catch (ParseException e) {
                return;
            }

            if (inizioTurnoM.before(calendar.getTime())) ;
            createNotify(turn.getInizioMattina(), turn.getFineMattina(),context);
            if (inizioTurnoP.before(calendar.getTime())) ;
            createNotify(turn.getInizioPomeriggio(), turn.getFinePomeriggio(),context);
        }
    }
}
