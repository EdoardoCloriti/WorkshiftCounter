package com.orion.workshiftmanager.selection;

import java.text.DateFormatSymbols;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.util.Week;
import com.orion.workshiftmanager.util.db.AccessToDB;

public class MounthSelection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mounth_selection);
        Button submit = (Button) findViewById(R.id.submit);
        Button back = (Button) findViewById(R.id.back);

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText mese = (EditText) findViewById(R.id.mese);
                EditText anno = (EditText) findViewById(R.id.anno);
                int month = Integer.parseInt(mese.getText().toString());
                int year = Integer.parseInt(anno.getText().toString());
                setContentView(R.layout.activity_display_mouth);
                Button back = (Button) findViewById(R.id.back);
                TextView meseAttuale = (TextView) findViewById(R.id.mese_attuale);
                TextView ore = (TextView) findViewById(R.id.ore);
                TextView straordinari = (TextView) findViewById(R.id.straordinari);
                meseAttuale.setText(new DateFormatSymbols().getMonths()[month - 1] + " " + year);
                ore.setText(calculateHour(month, year));
                straordinari.setText(calculateOvertime(month, year));
                back.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mounth_selection, menu);
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

    private String calculateHour(int mounth, int year) {
        AccessToDB db = new AccessToDB();
        List<Week> weeks = db.getMounth(mounth, year, getApplicationContext());
        double hours = 0;
        for (Week week : weeks) {
            hours = hours + week.getHour();
        }
        return Double.toString(hours);

    }

    private String calculateOvertime(int mounth, int year) {
        AccessToDB db = new AccessToDB();
        List<Week> weeks = db.getMounth(mounth, year, getApplicationContext());
        double hours = 0;
        for (Week week : weeks) {
            hours = hours + week.getExtraHour();
        }
        return Double.toString(hours);

    }
}
