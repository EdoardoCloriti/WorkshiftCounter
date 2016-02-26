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

import com.orion.workshiftmanager.selection.MultiSelectionMenu;
import com.orion.workshiftmanager.util.Property;
import com.orion.workshiftmanager.util.db.AccessToDB;

public class WorkShiftManager extends Activity {

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
