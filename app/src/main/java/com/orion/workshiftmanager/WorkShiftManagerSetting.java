package com.orion.workshiftmanager;

import org.xml.sax.ErrorHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.orion.workshiftmanager.selection.MultiSelectionMenu;
import com.orion.workshiftmanager.util.Property;
import com.orion.workshiftmanager.util.db.AccessToDB;
import com.orion.workshiftmanager.util.dev.DevelopmentDBUtility;
import com.orion.workshiftmanager.util.notification.WorkShiftCounterAlarmService;
import com.orion.workshiftmanager.util.notification.WorkShiftCounterNotificationService;

public class WorkShiftManagerSetting extends Activity {

    private Intent e = null;
    private Intent m = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_setting);

        AccessToDB db = new AccessToDB();
        e = new Intent(getApplicationContext(), ErrorHandler.class);
        m = new Intent(getApplicationContext(), MultiSelectionMenu.class);
        Button developmentUtility = (Button) findViewById(R.id.dev);
        Button submit = (Button) findViewById(R.id.submit);
        Button back = (Button) findViewById(R.id.back);

        setStateOre(db);
        setStateNotify(db);
        setStateAllarm(db);


        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText oreContratto = (EditText) findViewById(R.id.ore);
                CheckBox allarm = (CheckBox) findViewById(R.id.activeAllarm);
                CheckBox notify = (CheckBox) findViewById(R.id.notify);
                boolean check = true;
                String ore = oreContratto.getText().toString();
                AccessToDB db = new AccessToDB();
                if (ore.length() == 2 || ore.length() == 1) {
                    Property oreSettimanali = new Property();
                    oreSettimanali.setProperty(Property.ORESETTIMANALI);
                    oreSettimanali.setValue(ore);
                    db.insertProperty(oreSettimanali, getApplicationContext());
                } else {
                    check = false;
                }
                manageAlarm(allarm, db);
                mangaeNotify(notify, db);

                if (check) {
                    if (db.getProperty(Property.READYTOGO, getApplicationContext()) == null) {
                        Property ready = new Property();
                        ready.setProperty(Property.READYTOGO);
                        ready.setValue("true");
                        db.insertProperty(ready, getApplicationContext());
                        startActivity(m);
                        finish();
                    } else {
                        Property ready = new Property();
                        ready.setProperty(Property.READYTOGO);
                        ready.setValue("true");
                        db.insertProperty(ready, getApplicationContext());
                        startActivity(m);
                        finish();
                    }
                } else
                    startActivity(e);
            }
        });

        developmentUtility.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DevelopmentDBUtility.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // if (WorkshiftManagerTutorial.isTutorialReq(getApplicationContext(), getLocalClassName()))
        //   WorkshiftManagerTutorial.showWorkShiftManagerTurorial(getApplicationContext(), WorkshiftManagerTutorial.WORK_SHFIT_MANAGER_SETTING);
    }

    private void setStateOre(AccessToDB db) {
        EditText oreContratto = (EditText) findViewById(R.id.ore);
        if(db.existPropery( Property.ORESETTIMANALI,getApplicationContext())!=0)
        {
            Property ore=db.getProperty(Property.ORESETTIMANALI,getApplicationContext());
            CharSequence str=ore.getValue();
            oreContratto.setText(str, TextView.BufferType.EDITABLE);
        }
    }

    private void setStateAllarm(AccessToDB db) {
        CheckBox allarm = (CheckBox) findViewById(R.id.activeAllarm);
        if (db.existPropery(Property.ALLARM, getApplicationContext()) != 0) {
            Property notifyPr = db.getProperty(Property.ALLARM, getApplicationContext());
            if ("true".equals(notifyPr.getValue()))
                allarm.setChecked(true);
            else
                allarm.setChecked(false);
        } else
            allarm.setChecked(false);
    }

    private void setStateNotify(AccessToDB db) {
        CheckBox notify = (CheckBox) findViewById(R.id.notify);
        if (db.existPropery(Property.NOTIFICA, getApplicationContext()) != 0) {
            Property notifyPr = db.getProperty(Property.NOTIFICA, getApplicationContext());
            if ("true".equals(notifyPr.getValue()))
                notify.setChecked(true);
            else
                notify.setChecked(false);
        } else
            notify.setChecked(false);
    }

    private void manageAlarm(CheckBox allarm, AccessToDB db) {
        if (allarm.isChecked()) {
            Property activeAllarm = new Property();
            activeAllarm.setProperty(Property.ALLARM);
            activeAllarm.setValue("true");
            if (db.existPropery(activeAllarm, getApplicationContext()) != 0) {
                Property propertyOnDbdb = db.getProperty(Property.ALLARM, getApplicationContext());
                if (!activeAllarm.getValue().equals(propertyOnDbdb.getValue())) {
                    db.insertProperty(activeAllarm, getApplicationContext());
                    startService(new Intent(getApplicationContext(), WorkShiftCounterAlarmService.class));
                }
            } else {
                db.insertProperty(activeAllarm, getApplicationContext());
                startService(new Intent(getApplicationContext(), WorkShiftCounterAlarmService.class));
            }
        } else if (!allarm.isChecked()) {
            Property activeAllarm = new Property();
            activeAllarm.setProperty(Property.ALLARM);
            activeAllarm.setValue("false");
            if (db.existPropery(activeAllarm, getApplicationContext()) != 0) {
                Property propertyOnDbdb = db.getProperty(Property.ALLARM, getApplicationContext());
                if (!activeAllarm.getValue().equals(propertyOnDbdb.getValue())) {
                    db.insertProperty(activeAllarm, getApplicationContext());
                    stopService(new Intent(getApplicationContext(), WorkShiftCounterAlarmService.class));
                }
            } else {
                db.insertProperty(activeAllarm, getApplicationContext());
                stopService(new Intent(getApplicationContext(), WorkShiftCounterAlarmService.class));
            }
        }
    }


    private void mangaeNotify(CheckBox notify, AccessToDB db) {
        if (notify.isChecked()) {
            Property activeNotify = new Property();
            activeNotify.setProperty(Property.NOTIFICA);
            activeNotify.setValue("true");
            if (db.existPropery(activeNotify, getApplicationContext()) != 0) {
                Property propertyOnDbdb = db.getProperty(Property.NOTIFICA, getApplicationContext());
                if (!activeNotify.getValue().equals(propertyOnDbdb.getValue())) {
                    db.insertProperty(activeNotify, getApplicationContext());
                    startService(new Intent(getApplicationContext(), WorkShiftCounterNotificationService.class));
                }
            } else {
                db.insertProperty(activeNotify, getApplicationContext());
                startService(new Intent(getApplicationContext(), WorkShiftCounterNotificationService.class));
            }
        } else if (!notify.isChecked()) {
            Property activeNotify = new Property();
            activeNotify.setProperty(Property.NOTIFICA);
            activeNotify.setValue("false");
            if (db.existPropery(activeNotify, getApplicationContext()) != 0) {
                Property propertyOnDbdb = db.getProperty(Property.NOTIFICA, getApplicationContext());
                if (!activeNotify.getValue().equals(propertyOnDbdb.getValue())) {
                    db.insertProperty(activeNotify, getApplicationContext());
                    stopService(new Intent(getApplicationContext(), WorkShiftCounterNotificationService.class));
                }
            } else {
                db.insertProperty(activeNotify, getApplicationContext());
                stopService(new Intent(getApplicationContext(), WorkShiftCounterAlarmService.class));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.application_setting, menu);
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
