package com.orion.workshiftmanager.util.notification;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.TimeUnit;

/**
 * Created by Edoardo on 22/02/2016.
 */
public abstract class WorkShiftCounterService extends IntentService {

    protected static final String PATTERN = "dd/MM/yyyy";
    protected int hourToNotify = 0;
    protected Context context= this;

    public WorkShiftCounterService() {
        super("WorkShiftManager");
    }

    public void setHourToNotify(int hourToNotify) {
        this.hourToNotify = hourToNotify;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true) {
            specializedNotify();
            //sleep();
        }
    }

    private void sleep() {
        try {
            TimeUnit.HOURS.sleep(hourToNotify);
        } catch (InterruptedException e) {
            return;
        }
        catch (Exception e)
        {
            return;
        }
    }

    public abstract void specializedNotify();
}
