package com.orion.workshiftmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.orion.workshiftmanager.selection.MultiSelectionMenu;
import com.orion.workshiftmanager.util.Property;
import com.orion.workshiftmanager.util.db.AccessToDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkShiftManager extends Activity {

    private final static String PATTERN = "dd/MM/yyyy";
    private String CLEANING_DATE = "16/02/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        //Portait (Verticale)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // creazione del button di uscita dall'applicaizone
        Button exitButton = (Button) findViewById(R.id.exitbutton);
        // creazione del button di ingresso dell'appicazione
        Button startButton = (Button) findViewById(R.id.startbutton);
        super.onCreate(savedInstanceState);

        Calendar sysCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        try {
            CLEANING_DATE = CLEANING_DATE + sysCalendar.get(Calendar.YEAR);
            Date cleaning = sdf.parse(CLEANING_DATE);
            if (sysCalendar.getTime().equals(cleaning)) {
                AccessToDB db = new AccessToDB();
                int year = sysCalendar.get(Calendar.YEAR) - 1;
                db.clearTurnByYear(year, getApplicationContext());
                Toast.makeText(getApplicationContext(), "Effettuata pulizia annuale di turni", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Impossibile Effettuare la  pulizia annuale di turni", Toast.LENGTH_LONG).show();
        }


        startButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent multiSelectionMenu = new Intent(getApplicationContext(), MultiSelectionMenu.class);
                Intent applicationSetting = new Intent(getApplicationContext(), WorkShiftManagerSetting.class);

                AccessToDB db = new AccessToDB();
                if (db.getProperty(Property.READYTOGO, getApplicationContext()) == null) {
                    startActivity(applicationSetting);
                } else {
                    startActivity(multiSelectionMenu);
                }
            }
        });

        exitButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        //if (WorkshiftManagerTutorial.isTutorialReq(getApplicationContext(), getLocalClassName()))
        //  WorkshiftManagerTutorial.showWorkShiftManagerTurorial(getApplicationContext(), WorkshiftManagerTutorial.WORK_SHIFT_MANAGER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
