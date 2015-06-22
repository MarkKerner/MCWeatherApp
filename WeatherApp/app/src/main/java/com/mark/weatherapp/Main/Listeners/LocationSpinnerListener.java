package com.mark.weatherapp.Main.Listeners;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Mark on 22.06.2015.
 */
public class LocationSpinnerListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("mjedavidonat", Integer.toString(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
