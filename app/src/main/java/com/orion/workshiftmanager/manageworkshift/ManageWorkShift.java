package com.orion.workshiftmanager.manageworkshift;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.display.DisplayWorkShift;
import com.orion.workshiftmanager.util.IDs;
import com.orion.workshiftmanager.util.Turn;
import com.orion.workshiftmanager.util.db.AccessToDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class ManageWorkShift extends Activity {

    private static final int MONDAY = 1;
    private final List<Turn> turns = new ArrayList<Turn>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_work_shift);

        final CalendarView calendar = (CalendarView) findViewById(R.id.calendar);

        Button submit = (Button) findViewById(R.id.submit);
        Button back = (Button) findViewById(R.id.back);

        setCalendar(calendar);

        calendar.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar sysDate = Calendar.getInstance();

                Intent add = new Intent(getApplicationContext(), CreateWorkShift.class);
                Intent visual = new Intent(getApplicationContext(), DisplayWorkShift.class);
                AccessToDB accessToDB = new AccessToDB();
                Calendar selected = Calendar.getInstance();
                selected.set(year, month, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat(Turn.PATTERN);
                String selectedDay = new String(sdf.format(selected.getTime()));
                if (sysDate.before(selected)) {
                    Toast.makeText(getApplicationContext(), selectedDay, Toast.LENGTH_SHORT).show();

                    add.putExtra(IDs.DATA, selectedDay);
                    add.putExtra(IDs.WEEK_ID, selected.get(Calendar.WEEK_OF_YEAR));
                    add.putExtra(IDs.YEAR, year);
                    add.putExtra(IDs.MONTH, month + 1);
                    startActivityForResult(add, 1);
                } else {
                    Toast.makeText(getApplicationContext(), "Giorno selezionato non disponibile", Toast.LENGTH_SHORT).show();
                }
            }
        });

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AccessToDB db = new AccessToDB();
                try {
                    db.insertTurns(turns, getApplicationContext());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // if (WorkshiftManagerTutorial.isTutorialReq(getApplicationContext(), getLocalClassName()))
        //   WorkshiftManagerTutorial.showWorkShiftManagerTurorial(getApplicationContext(), WorkshiftManagerTutorial.MANAGE_WORKSHIFT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_work_shift, menu);
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
        calendar.setFirstDayOfWeek(MONDAY);
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.transparent));
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));
        calendar.setSelectedDateVerticalBar(R.color.DarkGray);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        Bundle bundle = arg2.getExtras();
        if (!bundle.containsKey("back")) {
            Turn turn = Turn.turnByBundle(bundle);
            turn.setHour();
            turns.add(turn);
        }
    }
}
