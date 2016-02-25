package com.orion.workshiftmanager.util.dev;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.util.Property;
import com.orion.workshiftmanager.util.db.DbAdapter;

public class DevelopmentDBUtility extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development_dbutility);
        Button resetSettingDB = (Button) findViewById(R.id.reset_setting);
        Button resetTurnDB = (Button) findViewById(R.id.reset_turn);
        Button resetHourDB = (Button) findViewById(R.id.reset_hour);
        Button back = (Button) findViewById(R.id.back);

        resetSettingDB.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DbAdapter dbAdapter = new DbAdapter(getApplicationContext());
                dbAdapter.open();
                if (dbAdapter.resetSettingDB())
                    Toast.makeText(getApplicationContext(), "Tabella resettata con successo", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Impossibile resettare la tabella", Toast.LENGTH_SHORT).show();
                dbAdapter.close();

            }
        });

        resetTurnDB.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DbAdapter dbAdapter = new DbAdapter(getApplicationContext());
                dbAdapter.open();
                if (dbAdapter.resetTurnDB())
                    Toast.makeText(getApplicationContext(), "Tabella resettata con successo", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Impossibile resettare la tabella", Toast.LENGTH_SHORT).show();
                dbAdapter.close();
            }
        });

        resetHourDB.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DbAdapter dbAdapter = new DbAdapter(getApplicationContext());
                dbAdapter.open();
                if (dbAdapter.resetHourDB())
                    Toast.makeText(getApplicationContext(), "Tabella resettata con successo", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Impossibile resettare la tabella", Toast.LENGTH_SHORT).show();
                dbAdapter.close();
            }
        });

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DbAdapter dbAdapter = new DbAdapter(getApplicationContext());
                CheckBox devMode = (CheckBox) findViewById(R.id.devmode);
                if (devMode.isActivated()) {
                    dbAdapter.open();
                    dbAdapter.creaProperty(Property.DEVMODE, "activated");
                    dbAdapter.close();
                } else {
                    dbAdapter.open();
                    dbAdapter.creaProperty(Property.DEVMODE, "disactive");
                    dbAdapter.close();
                }
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.development_dbutility, menu);
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
