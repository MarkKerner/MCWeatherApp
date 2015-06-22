package com.mark.weatherapp.Main.GUI;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mark.weatherapp.Main.Adapters.MainViewPagerAdapter;
import com.mark.weatherapp.Main.Handlers.HandleXML;
import com.mark.weatherapp.Main.Listeners.LocationSpinnerListener;
import com.mark.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    public static HandleXML sObj;
    public static Location sLastKnownLocation;
    private MainViewPagerAdapter mMainViewPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private SlidingTabLayout mSlidingTabs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("WeatherApp", "Layout tehtud");
        sObj = new HandleXML();
        Log.e("WeatherApp", "fetchXML tehtud");
        while (sObj.parsingComplete) ;
        Log.e("WeatherApp", "Parsing tehtud");



        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        sLastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.e("location", sLastKnownLocation.toString());



        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        setSpinnerValues();


        mSlidingTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabs.setDistributeEvenly(true);
        mSlidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        mSlidingTabs.setViewPager(mViewPager);


    }

    private void setViewPagerAdapter() {

        mMainViewPagerAdapter =
                new MainViewPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mMainViewPagerAdapter);

    }

    private void setSpinnerValues() {
        Spinner locationSpinner = (Spinner) findViewById(R.id.location_spinner);
        List<String> locationList = new ArrayList<>();

        for (String location : sObj.getDates().get(0).getLocationNight().keySet()) {
            locationList.add(location);
        }

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, locationList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        locationSpinner.setOnItemSelectedListener(new LocationSpinnerListener());

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