package com.orion.workshiftmanager.manageworkshift;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.util.IDs;

public class CreateWorkShift extends Activity {

    Intent outputIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_work_shift);
        final Bundle inputBundle = this.getIntent().getExtras();
        outputIntent = new Intent(getApplicationContext(), ManageWorkShift.class);
        TextView title = (TextView) findViewById(R.id.title_add_turn_menu);
        Button insertMattina = (Button) findViewById(R.id.inserisci_mattina);
        Button insertPomeriggio = (Button) findViewById(R.id.inserisci_pomeriggio);
        Button back = (Button) findViewById(R.id.back);
        // gestione del titolo della pagina -> default senza data... inserimento della data di riferimento
        outputIntent.putExtra(IDs.DATA, inputBundle.getString(IDs.DATA));
        outputIntent.putExtra(IDs.WEEK_ID, inputBundle.getInt(IDs.WEEK_ID));
        outputIntent.putExtra(IDs.YEAR, inputBundle.getInt(IDs.YEAR));
        outputIntent.putExtra(IDs.MONTH, inputBundle.getInt(IDs.MONTH));
        title.setText(getString(R.string.title_turn_menu) + "\r\n" + inputBundle.getString(IDs.DATA));
        // gestione dell'inserimento della mattina
        insertMattina.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent selectTurnCalendar = new Intent(getApplicationContext(), SelectHours.class);
                selectTurnCalendar.putExtra(IDs.PART_OF_DAY, "am");
                outputIntent.putExtra(IDs.PART_OF_DAY, "am");
                startActivityForResult(selectTurnCalendar, IDs.SelectTurn);
            }

        });
        // gestione dell'inserimento del pomeriggio
        insertPomeriggio.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent selectTurnCalendar = new Intent(getApplicationContext(), SelectHours.class);
                selectTurnCalendar.putExtra(IDs.PART_OF_DAY, "pm");
                outputIntent.putExtra(IDs.PART_OF_DAY, "pm");
                startActivityForResult(selectTurnCalendar, 2);
            }
        });
        // gestione del tasto back
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(1, outputIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_work_shift, menu);
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

    @Override
    public void onBackPressed() {
        Intent out = new Intent();
        out.putExtra("back", 1);
        setResult(1, out);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            Bundle result = data.getExtras();
            if (!result.containsKey("back")) {
                if (result.getString(IDs.ORARIO_MATTINA) != null) {
                    String[] orarioMattina = result.getString(IDs.ORARIO_MATTINA).split("-");
                    outputIntent.putExtra(IDs.INIZIO_MATTINA, orarioMattina[0]);
                    outputIntent.putExtra(IDs.FINE_MATTINA, orarioMattina[1]);
                }

                if (result.getString(IDs.ORARIO_POMERIGGIO) != null) {
                    String[] orarioPomeriggio = result.getString(IDs.ORARIO_POMERIGGIO).split("-");
                    outputIntent.putExtra(IDs.INIZIO_POMERIGGIO, orarioPomeriggio[0]);
                    outputIntent.putExtra(IDs.FINE_POMERIGGIO, orarioPomeriggio[1]);
                }
                outputIntent.putExtra(IDs.PRIORITY, result.getBoolean(IDs.PRIORITY));
            }
        }
    }
}
