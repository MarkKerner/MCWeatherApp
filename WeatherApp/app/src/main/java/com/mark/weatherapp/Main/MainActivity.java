package com.mark.weatherapp.Main;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mark.weatherapp.Main.Handlers.HandleXML;
import com.mark.weatherapp.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private HandleXML obj;
    private TextView tempView;
    private TextView weatherTextView;
    private Spinner dateSpinner;
    List<Date> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("WeatherApp", "Layout tehtud");
        obj = new HandleXML();
        Log.e("WeatherApp", "fetchXML tehtud");
        while (obj.parsingComplete) ;
        Log.e("WeatherApp", "Parsing tehtud");
        doSpinner();
        setStartupValues();
    }

    private void setStartupValues() {
        Date date = obj.getDates().get(0);
        setValues(date);
    }

    public void setValues(Date date) {
        tempView = (TextView) findViewById(R.id.temp_TextView);
        weatherTextView = (TextView) findViewById(R.id.weathertext_TextView);

        String tempmin = date.getDay().get("tempmin");
        String tempmax = date.getDay().get("tempmax");
        String phenomenon = date.getDay().get("phenomenon");

        tempView.setText(tempmin + "..." + tempmax);
        weatherTextView.setText(phenomenon);
    }

    private void doSpinner() {
        dates = obj.getDates();
        dateSpinner = (Spinner) findViewById(R.id.date_Spinner);
        List<String> list = new ArrayList<>();

        for (Date date : dates) {
            list.add(date.getDate());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(dataAdapter);
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedDate = parent.getItemAtPosition(pos).toString();
                for (Date date : dates) {
                    if (date.getDate().equalsIgnoreCase(selectedDate)) {
                        setValues(date);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

