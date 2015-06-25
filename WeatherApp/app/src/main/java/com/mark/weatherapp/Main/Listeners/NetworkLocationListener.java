package com.mark.weatherapp.Main.Listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.mark.weatherapp.Main.GUI.MainActivity;

public class NetworkLocationListener implements LocationListener {

    //Seab uue asukoha leidmisel selle muutujuga. Kuna asukoha täpsus pole eriliselt oluline, siis pole keerulisem loogika siin väga vajalik.
    public void onLocationChanged(Location location) {
        MainActivity.sLastKnownLocation = location;
        Log.d("New location acquired", MainActivity.sLastKnownLocation.toString());
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}
