package com.mark.weatherapp.Main.GUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mark.weatherapp.Main.System.Date;
import com.mark.weatherapp.R;

/**
 * Fragment, mis luuakse esimese päeva korral
 */
public class DayOneFragment extends android.support.v4.app.Fragment {
    private Date date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maindate, container, false);

        date = MainActivity.sRSSObj.getDates().get(0);
        SetViewValues.setValues(date, v);
        return v;
    }
}
