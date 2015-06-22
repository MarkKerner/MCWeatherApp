package com.mark.weatherapp.Main.GUI;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
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
import com.mark.weatherapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    public static final String LAST_LOCATION_NAME = "LastLocationFile";

    public static HandleXML sObj;
    public static Location sLastKnownLocation;
    public static String sLocationName;
    private MainViewPagerAdapter mMainViewPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private SlidingTabLayout mSlidingTabs;
    private Spinner locationSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(LAST_LOCATION_NAME, MODE_PRIVATE);
        int savedLocationIndex = settings.getInt("last_location", 0);
        if (savedLocationIndex > 7) {
            savedLocationIndex = 0;
        }

        Log.e("WeatherApp", "Layout tehtud");
        sObj = new HandleXML();
        Log.e("WeatherApp", "fetchXML tehtud");
        while (sObj.parsingComplete) ;
        Log.e("WeatherApp", "Parsing tehtud");


        /**
         LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
         sLastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
         Log.e("location", sLastKnownLocation.toString());
         **/

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        locationSpinner = (Spinner) findViewById(R.id.location_spinner);
        setSpinnerValues();

        locationSpinner.setSelection(savedLocationIndex);

        sLocationName = locationSpinner.getSelectedItem().toString();


        mViewPager = (ViewPager) findViewById(R.id.pager);
        List<Fragment> fragments = null;

        if (sLocationName.equalsIgnoreCase("Eesti")) {
            Fragment[] fragmentArray = {new DayOneFragment(), new DayTwoFragment(), new DayThreeFragment(), new DayFourFragment()};
            fragments = new ArrayList<>(Arrays.asList(fragmentArray));
        }else {
            Fragment[] fragmentArray = {new LocationFragment()};
            fragments = new ArrayList<>(Arrays.asList(fragmentArray));
        }

        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mMainViewPagerAdapter.setmFragments(fragments);
        mMainViewPagerAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(mMainViewPagerAdapter);


        mSlidingTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabs.setDistributeEvenly(true);
        mSlidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        //mSlidingTabs.setViewPager(mViewPager);


    }

    private void setSpinnerValues() {
        List<String> locationList = new ArrayList<>();

        locationList.add("Eesti");
        for (int i = 1; i <= 6; i++) {
            locationList.add(SetViewValues.LOCATION_MAP.get(i));
        }

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, locationList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sLocationName = locationSpinner.getSelectedItem().toString();

                if (sLocationName.equalsIgnoreCase("Eesti")) {
                    Fragment[] fragmentArray = {new DayOneFragment(), new DayTwoFragment(), new DayThreeFragment(), new DayFourFragment()};
                    List<Fragment> fragments = new ArrayList<>(Arrays.asList(fragmentArray));

                    mMainViewPagerAdapter.setmFragments(fragments);
                    mMainViewPagerAdapter.notifyDataSetChanged();

                    Log.e("fragmendid", mMainViewPagerAdapter.getmFragments().toString());
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

            }
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

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(LAST_LOCATION_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("last_location", locationSpinner.getSelectedItemPosition());

        editor.commit();
    }
}