package com.mark.weatherapp.Main.GUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mark.weatherapp.Main.Adapters.MainViewPagerAdapter;
import com.mark.weatherapp.Main.Handlers.HandleXML;
import com.mark.weatherapp.Main.Listeners.NetworkLocationListener;
import com.mark.weatherapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LAST_LOCATION_NAME = "LastLocationFile";

    public static HandleXML sRSSObj;
    public static Location sLastKnownLocation;
    public static String sLocationName;
    private MainViewPagerAdapter mMainViewPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private SlidingTabLayout mSlidingTabs;
    private Spinner mLocationSpinner;
    private int mLastLocationSpinnerIndex;
    private Location mLastPosition;
    private LocationManager mLocationManager;
    private boolean canGetLocation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mViewPager = (ViewPager) findViewById(R.id.pager);

        //Programm ootab vajadusel, kuni internetiühendus on loodud
        while (!isNetworkAvailable()) ;

        //Tõmbab RSS voo, töötleb selle ja salvestab objekti sRSSObj
        getXMLData();

        //Taastab salvestatud andmed, kui need on
        getPreferenceData();

        //Üritab asukoha info saada, et selle järgi tuule asukoht määrata
        getLocationData();

        //Määrab RSS voo põhjal spinneri elemendid
        setSpinnerValues();

        //Teeb tabid
        makeSlidingTabs();

        //Seab esialgse kuva valmis
        setStartingScreen();
    }


    private void getLocationData() {
        sLastKnownLocation = getLocation();

        //Kui asukohta pole võimalik saada, aga taastati vana asukoht, kasutatakse seda. Kui vana asukohta ka pole, pannakse asukohaks Kuusiku.
        if (canGetLocation == false && mLastPosition != null) {
            sLastKnownLocation = mLastPosition;
        } else {
            Log.d("Location", "No location data available, set default");
            sLastKnownLocation = SetViewValues.LOCATION_KUUSIKU;
        }
    }

    /**
     * Leiab asukoha ja seab LocationListeneri, kui see võimalik on
     *
     * @return tagastab viimase teadaoleva asukoha või null
     */
    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) this
                    .getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled

            } else {
                canGetLocation = true;
                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            0,
                            0.0f, new NetworkLocationListener());
                    Log.d("Network", "Network Enabled");
                    if (mLocationManager != null) {
                        sLastKnownLocation = mLocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (sLastKnownLocation == null) {
                        mLocationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                0,
                                0.0f, new NetworkLocationListener() {
                                });
                        Log.d("GPS", "GPS Enabled");
                        if (mLocationManager != null) {
                            sLastKnownLocation = mLocationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sLastKnownLocation;
    }

    /**
     * Kontrollib, kas internetiühendus on olemas
     *
     * @return tagastab tõeväärtuse, kas ühendus on olemas
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Teeb tabid
     */
    private void makeSlidingTabs() {
        mSlidingTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabs.setDistributeEvenly(true);
        mSlidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

    }

    /**
     * Seab esialgse kuva vastavalt kasutaja salvestatud valikule
     */
    private void setStartingScreen() {
        List<Fragment> fragments;

        if (sLocationName.equalsIgnoreCase("Eesti")) {
            Fragment[] fragmentArray = {new DayOneFragment(), new DayTwoFragment(), new DayThreeFragment(), new DayFourFragment()};
            fragments = new ArrayList<>(Arrays.asList(fragmentArray));
        } else {
            Fragment[] fragmentArray = {new LocationFragment()};
            fragments = new ArrayList<>(Arrays.asList(fragmentArray));
        }
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mMainViewPagerAdapter);
        mSlidingTabs.setViewPager(mViewPager);
    }

    /**
     * Teeb uue RSS töötlemise objekti ja ootab, kuni RSS on loetud ja parsitud.
     */
    private void getXMLData() {
        Log.d("WeatherApp", "Layout done");
        sRSSObj = new HandleXML();
        Log.d("WeatherApp", "Fetching done");
        while (sRSSObj.parsingComplete) ;
        Log.d("WeatherApp", "Parsing done");

    }

    /**
     * Taastab salvestatud andmed
     */
    private void getPreferenceData() {
        SharedPreferences settings = getSharedPreferences(LAST_LOCATION_NAME, MODE_PRIVATE);

        //Taastab viimase spinner asukoha
        mLastLocationSpinnerIndex = settings.getInt("last__spinnerlocation", 0);
        if (mLastLocationSpinnerIndex > 6 || mLastLocationSpinnerIndex < 0) {
            mLastLocationSpinnerIndex = 0;
        }

        //Taastab viimase kasutaja asukoha, kui see oli olemas
        double lastPositionLat = (double) settings.getFloat("last_position_lat", 0);
        double lastPositionLon = (double) settings.getFloat("last_position_lon", 0);

        if (lastPositionLat == 0 && lastPositionLon == 0) {
            mLastPosition = null;
        } else {
            mLastPosition = new Location("savedLocation");
            mLastPosition.setLatitude(lastPositionLat);
            mLastPosition.setLongitude(lastPositionLon);
        }
    }

    /**
     * Määrab spinner väärtused ja selekteerib eelnevalt valitud asukoha
     */
    private void setSpinnerValues() {
        mLocationSpinner = (Spinner) findViewById(R.id.location_spinner);


        //Teeb listi asukohta nimedega
        List<String> locationList = new ArrayList<>();
        locationList.add("Eesti");
        for (int i = 1; i <= 6; i++) {
            locationList.add(SetViewValues.LOCATION_MAP.get(i));
        }

        //Seab spinnerile adapteri elementidega asukohtade listist
        ArrayAdapter<String> locationSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, locationList);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLocationSpinner.setAdapter(locationSpinnerAdapter);

        //Seab spinnerile listeneri, mis näitab vastavaid fragmente olenevalt valikust
        mLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sLocationName = mLocationSpinner.getSelectedItem().toString();


                if (sLocationName.equalsIgnoreCase("Eesti")) {
                    Fragment[] fragmentArray = {new DayOneFragment(), new DayTwoFragment(), new DayThreeFragment(), new DayFourFragment()};
                    List<Fragment> fragments = new ArrayList<>(Arrays.asList(fragmentArray));

                    mMainViewPagerAdapter.setmFragments(fragments);
                    mMainViewPagerAdapter.notifyDataSetChanged();
                    mSlidingTabs.setViewPager(mViewPager);

                } else {
                    Fragment[] fragmentArray = {new LocationFragment()};
                    List<Fragment> fragments = new ArrayList<>(Arrays.asList(fragmentArray));

                    mMainViewPagerAdapter.setmFragments(fragments);

                    mMainViewPagerAdapter.notifyDataSetChanged();
                    mSlidingTabs.setViewPager(mViewPager);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Alati on midagi valitud, nii et see meetod ei ole vajalik
            }
        });

        mLocationSpinner.setSelection(mLastLocationSpinnerIndex);
        sLocationName = mLocationSpinner.getSelectedItem().toString();
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


    //Salvestab kasutaja spinneri valiku ja viimase asukoha
    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(LAST_LOCATION_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("last_spinner_location", mLocationSpinner.getSelectedItemPosition());
        editor.putFloat("last_position_lat", (float) sLastKnownLocation.getLatitude());
        editor.putFloat("last_position_lon", (float) sLastKnownLocation.getLongitude());

        editor.commit();
    }
}