package com.orion.workshiftmanager.display;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.util.Turn;
import com.orion.workshiftmanager.util.db.AccessToDB;

public class DisplayTurn extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        final Bundle inputBundle = this.getIntent().getExtras();
        String selectedDay = inputBundle.getString("SELECTED_DAY");
        AccessToDB db = new AccessToDB();
        Turn turn = db.getTurnBySelectedDay(selectedDay, getApplicationContext());
        setContentView(R.layout.activity_display_turn);

        TextView title = (TextView) findViewById(R.id.title);

        TextView inizioMattina = (TextView) findViewById(R.id.iniziovalue);
        TextView fineMattina = (TextView) findViewById(R.id.finevalue);

        TextView inizioPomeriggio = (TextView) findViewById(R.id.iniziopomeriggio);
        TextView finePomeriggio = (TextView) findViewById(R.id.finepomeriggio);

        TextView importante = (TextView) findViewById(R.id.importante);
        TextView overValue = (TextView) findViewById(R.id.overvalue);
        TextView orevalue = (TextView) findViewById(R.id.orevalue);

        Button back = (Button) findViewById(R.id.back);

        title.setText(turn.getDatariferimento());

        if (turn.getInizioMattina() != null && turn.getFineMattina() != null && !isNull(turn.getInizioMattina(), turn.getFineMattina())) {
            inizioMattina.setText(turn.getInizioMattina());
            fineMattina.setText(turn.getFineMattina());
        } else {
            inizioMattina.setText(R.string.riposo);
            fineMattina.setText(R.string.riposo);
        }

        if (turn.getInizioPomeriggio() != null && turn.getFinePomeriggio() != null && !isNull(turn.getInizioPomeriggio(), turn.getFinePomeriggio())) {
            inizioPomeriggio.setText(turn.getInizioPomeriggio());
            finePomeriggio.setText(turn.getFinePomeriggio());
        } else {
            inizioPomeriggio.setText(R.string.riposo);
            finePomeriggio.setText(R.string.riposo);
        }

        orevalue.setText(Double.toString(turn.getHour()));
        overValue.setText(Double.toString(turn.getOvertime()));

        if (turn.getIsImportante()) {
            importante.setTextColor(getResources().getColor(R.color.Red));
        } else {
            importante.setTextColor(getResources().getColor(R.color.transparent));
        }
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private boolean isNull(String value1, String value2) {
        return "null:null".equalsIgnoreCase(value1) && "null:null".equalsIgnoreCase(value2);
    }

}
