package com.mark.weatherapp.Main.GUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mark.weatherapp.R;

/**
 * Created by Mark on 22.06.2015.
 */
public class LocationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_location, container, false);

        int locationIndex = SetViewValues.LOCATION_MAP_REVERSED.get(MainActivity.sLocationName);

        SetViewValues.setLocationValues(locationIndex, v);

        return v;
    }


}
