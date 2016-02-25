package com.orion.workshiftmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.orion.workshiftmanager.util.Turn;
import com.orion.workshiftmanager.util.Week;
import com.orion.workshiftmanager.util.db.AccessToDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DisplaySettimana extends Activity {
    private static final int MONDAY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settimana);
        final CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        Button back = (Button) findViewById(R.id.back);

        setCalendar(calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                GregorianCalendar day = new GregorianCalendar(year, month, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat(Turn.PATTERN);
                String selectedDay = new String(sdf.format(day.getTime()));
                Toast.makeText(getApplicationContext(), selectedDay, Toast.LENGTH_SHORT).show();
                AccessToDB db = new AccessToDB();
                Calendar cal = Calendar.getInstance();
                cal.setTime(day.getTime());
                Week week=db.getWeeekByCorrelationId(year,cal.get(Calendar.WEEK_OF_YEAR),getApplicationContext());
                visualWeek(week);
            }

        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setCalendar(CalendarView calendar) {
        calendar.setShowWeekNumber(false);
        // setto come primo giorno della settimana il lunedì
        calendar.setFirstDayOfWeek(MONDAY);
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.DodgerBlue));
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);
    }

    private void visualWeek(Week week) {
        setContentView(R.layout.activity_week_hour);
        TextView ore=(TextView) findViewById(R.id.ore);
        TextView straordinari=(TextView) findViewById(R.id.straordinari);
        Button back=(Button) findViewById(R.id.back);
        if(week!=null) {
            CharSequence oreCs = new Double(week.getHour()).toString();
            CharSequence straordinariCs = new Double(week.getExtraHour()).toString();
            ore.setText(oreCs);
            straordinari.setText(straordinariCs);
        }else
        {
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


