package com.orion.workshiftmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orion.workshiftmanager.util.Week;
import com.orion.workshiftmanager.util.db.AccessToDB;

public class DisplayHourWeek extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccessToDB db = new AccessToDB();
        Bundle input = getIntent().getExtras();
        int mounth = input.getInt("MOUNTH");
        int year = input.getInt("YEAR");
        Week week = db.getWeeekByCorrelationId(year, mounth, getApplicationContext());
        setContentView(R.layout.activity_display_hour_week);
        TextView ore = (TextView) findViewById(R.id.ore);
        TextView straordinari = (TextView) findViewById(R.id.straordinari);
        Button back = (Button) findViewById(R.id.back);
        if (week != null) {
            CharSequence oreCs = new Double(week.getHour()).toString();
            CharSequence straordinariCs = new Double(week.getExtraHour()).toString();
            ore.setText(oreCs);
            straordinari.setText(straordinariCs);
        } else {
            CharSequence oreCs = new Double(0).toString();
            CharSequence straordinariCs = new Double(0).toString();
            ore.setText(oreCs);
            straordinari.setText(straordinariCs);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
