package com.orion.workshiftmanager.manageworkshift;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.util.IDs;
import com.orion.workshiftmanager.util.Turn;
import com.orion.workshiftmanager.util.db.AccessToDB;
import com.orion.workshiftmanager.util.tutorial.WorkshiftManagerTutorial;

public class StarlingHours extends Activity {

    private static final int MONDAY = 1;
    private List<Turn> turns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starling_hours);

        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        Button submit = (Button) findViewById(R.id.submit);
        Button back = (Button) findViewById(R.id.back);

        turns = new ArrayList<Turn>();
        setCalendar(calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent select = new Intent(getApplicationContext(), SelectOvertime.class);
                GregorianCalendar day = new GregorianCalendar(year, month, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat(Turn.PATTERN);
                String selectedDay = new String(sdf.format(day.getTime()));
                Toast.makeText(getApplicationContext(), selectedDay, Toast.LENGTH_SHORT).show();
                select.putExtra(IDs.DATA, selectedDay);
                startActivityForResult(select, 1);
            }
        });

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AccessToDB access = new AccessToDB();
                int n = access.updateTurn(turns, getApplicationContext());
                turns = null;
                Toast.makeText(getApplicationContext(), "inseriti:" + n + "giorni di straordinari", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (WorkshiftManagerTutorial.isTutorialReq(getApplicationContext(), getLocalClassName()))
            WorkshiftManagerTutorial.showWorkShiftManagerTurorial(getApplicationContext(), WorkshiftManagerTutorial.STARLING_HOURS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starling_hours, menu);
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


    private double validateMinutes(int newVal) {
        if (newVal > 60)
            allert("m", newVal);
        else {
            if (newVal < 15)
                return 0;
            else if (newVal < 30)
                return 0.25;
            else if (newVal < 45)
                return 0.5;
            else if (newVal < 60)
                return 0.75;
        }
        return 0;
    }

    private int validateHours(int newVal) {
        if (newVal > 60) {
            allert("h", newVal);
            return 0;
        } else
            return newVal;
    }

    private void allert(String picker, int value) {
        String message = null;
        if ("h".equalsIgnoreCase(picker))
            message = getString(R.string.msg_numero_ore) + value + getString(R.string.msg_orario_non_corretto) + "24";
        else if ("m".equalsIgnoreCase(picker))
            message = getString(R.string.msg_numero_minuti) + value + getString(R.string.msg_orario_non_corretto) + "60";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setTitle(getString(R.string.msg_valore_non_corretto));
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(getString(R.string.msg_ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        Bundle bundle = arg2.getExtras();
        if (!bundle.containsKey("back")) {
            String referencesDate = null;
            if (bundle.containsKey(IDs.DATA))
                referencesDate = bundle.getString(IDs.DATA);
            double overtime = bundle.getDouble(IDs.OVERTIME);
            AccessToDB db = new AccessToDB();
            Turn turn = db.getTurnBySelectedDay(referencesDate, getApplicationContext());
            overtime = turn.getOvertime() - overtime;
            turn.setOvertime(overtime);
            turns.add(turn);
        }
    }
}
