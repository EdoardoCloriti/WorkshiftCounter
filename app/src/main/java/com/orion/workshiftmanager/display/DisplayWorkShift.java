package com.orion.workshiftmanager.display;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.util.Turn;
import com.orion.workshiftmanager.util.db.AccessToDB;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DisplayWorkShift extends Activity {
    private static final int MONDAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_work_shift);

        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        Button back = (Button) findViewById(R.id.back);

        setCalendar(calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent i = new Intent(getApplicationContext(), DisplayTurn.class);
                GregorianCalendar day = new GregorianCalendar(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(Turn.PATTERN);
                String selectedDay = new String(sdf.format(day.getTime()));
                Toast.makeText(getApplicationContext(), selectedDay, Toast.LENGTH_SHORT).show();
                AccessToDB db = new AccessToDB();
                Turn turn = db.getTurnBySelectedDay(selectedDay, getApplicationContext());
                Turn.intentByTurn(i, turn);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_work_shift, menu);
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

    private void setCalendar(CalendarView calendar) {
        calendar.setShowWeekNumber(false);
        // setto come primo giorno della settimana il lunedì
        calendar.setFirstDayOfWeek(MONDAY);
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.DodgerBlue));
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);
    }

}
