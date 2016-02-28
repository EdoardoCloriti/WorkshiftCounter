package com.orion.workshiftmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.orion.workshiftmanager.util.Turn;

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
                Intent i = new Intent(getApplicationContext(), DisplayHourWeek.class);
                SimpleDateFormat sdf = new SimpleDateFormat(Turn.PATTERN);
                String selectedDay = new String(sdf.format(day.getTime()));
                Toast.makeText(getApplicationContext(), selectedDay, Toast.LENGTH_SHORT).show();
                Calendar cal = Calendar.getInstance();
                cal.setTime(day.getTime());
                int mounth = cal.get(Calendar.WEEK_OF_YEAR);
                i.putExtra("MOUNTH", mounth);
                i.putExtra("YEAR", year);
                startActivity(i);
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
        calendar.setFirstDayOfWeek(MONDAY);
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.transparent));
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));
        calendar.setSelectedDateVerticalBar(R.color.DarkGray);
    }
}


