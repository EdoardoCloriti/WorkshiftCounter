package com.orion.workshiftmanager.selection;

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

import com.orion.workshiftmanager.DisplaySettimana;
import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.WorkShiftManagerSetting;
import com.orion.workshiftmanager.display.DisplayWorkShift;
import com.orion.workshiftmanager.display.DisplayYear;
import com.orion.workshiftmanager.manageworkshift.AddOvertime;
import com.orion.workshiftmanager.manageworkshift.ManageWorkShift;
import com.orion.workshiftmanager.manageworkshift.StarlingHours;
import com.orion.workshiftmanager.util.tutorial.WorkshiftManagerTutorial;

public class MultiSelectionMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        //Portait (Verticale)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(R.layout.activity_multi_selection_menu);

        Button manageWorkShift = (Button) findViewById(R.id.insertT);
        Button displayTurn = (Button) findViewById(R.id.visualTurn);
        Button addOvertime = (Button) findViewById(R.id.addextraordinary);
        Button back = (Button) findViewById(R.id.backbutton);
        Button displayMounth = (Button) findViewById(R.id.displayH);
        Button displayYear = (Button) findViewById(R.id.displayY);
        Button displayOreSettimali=(Button) findViewById(R.id.oreSettimanali);
        Button starlingHour = (Button) findViewById(R.id.storno);
        Button setting = (Button) findViewById(R.id.setting);

        manageWorkShift.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManageWorkShift.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

        displayTurn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DisplayWorkShift.class);
                startActivity(i);
            }
        });

        addOvertime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddOvertime.class);
                startActivity(i);
            }
        });
        starlingHour.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StarlingHours.class);
                startActivity(i);
            }
        });
        displayYear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DisplayYear.class);
                startActivity(i);
            }
        });

        displayMounth.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MounthSelection.class);
                startActivity(i);
            }
        });
        setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WorkShiftManagerSetting.class);
                startActivity(i);
            }
        });

        //TODO:
        displayOreSettimali.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DisplaySettimana.class);
                startActivity(i);
            }
        });

        //   if (WorkshiftManagerTutorial.isTutorialReq(getApplicationContext(), getLocalClassName()))
        //     WorkshiftManagerTutorial.showWorkShiftManagerTurorial(getApplicationContext(), WorkshiftManagerTutorial.MULTI_SELECTION_MENU);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.multi_selection_menu, menu);
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
