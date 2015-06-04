package com.mark.weatherapp.Main.GUI;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mark.weatherapp.Main.Handlers.HandleXML;
import com.mark.weatherapp.R;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    private FragmentTabHost mTabHost;
    private static HandleXML mObj;
    public static final Map<String, String> PHENOMEN_MAP =
            new HashMap<>();
    static {
        PHENOMEN_MAP.put("Clear","Selge");
        PHENOMEN_MAP.put("Few clouds","Vähene pilvisus");
        PHENOMEN_MAP.put("Variable clouds","Vahelduv pilvisus");
        PHENOMEN_MAP.put("Cloudy with clear spells","Pilves selginemistega");
        PHENOMEN_MAP.put("Cloudy","Pilves");
        PHENOMEN_MAP.put("Light snow shower","Nõrk hooglumi");
        PHENOMEN_MAP.put("Moderate snow shower","Mõõdukas hooglumi");
        PHENOMEN_MAP.put("Heavy snow shower","Tugev hooglumi");
        PHENOMEN_MAP.put("Light shower","Nõrk hoogvihm");
        PHENOMEN_MAP.put("Moderate shower","Keskmine hoogvihm");
        PHENOMEN_MAP.put("Heavy shower","Tugev hoogvihm");
        PHENOMEN_MAP.put("Light rain","Nõrk vihm");
        PHENOMEN_MAP.put("Moderate rain","Mõõdukas vihm");
        PHENOMEN_MAP.put("Heavy rain","Tugev vihm");
        PHENOMEN_MAP.put("Risk of glaze","Jäiteoht");
        PHENOMEN_MAP.put("Light sleet","Nõrk lörtsisadu");
        PHENOMEN_MAP.put("Moderate sleet","Mõõdukas lörtisadu");
        PHENOMEN_MAP.put("Light snowfall","Nõrk lumesadu");
        PHENOMEN_MAP.put("Moderate snowfall","Mõõdukas lumesadu");
        PHENOMEN_MAP.put("Heavy snowfall","Tugev lumesadu");
        PHENOMEN_MAP.put("Snowstorm","Üldtuisk");
        PHENOMEN_MAP.put("Drifting snow","Pinnatuisk");
        PHENOMEN_MAP.put("Hail","Rahe");
        PHENOMEN_MAP.put("Mist","Uduvine");
        PHENOMEN_MAP.put("Fog","Udu");
        PHENOMEN_MAP.put("Thunder","Äike");
        PHENOMEN_MAP.put("Thunderstorm","Äikesetorm");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DayOneFragment da = new DayOneFragment();

        Log.e("WeatherApp", "Layout tehtud");
        mObj = new HandleXML();
        Log.e("WeatherApp", "fetchXML tehtud");
        while (mObj.parsingComplete) ;
        Log.e("WeatherApp", "Parsing tehtud");
        sendDataToFragments();
    }

    private void sendDataToFragments() {
        Bundle bundle;

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        Log.e("WeatherApp", "Setup tehtud");

        for (int i = 0; i < 4; i++) {

            bundle = new Bundle();

            bundle.putSerializable("day" + (i + 1), mObj.getDates().get(i));
            Log.e("SendData", "day" + (i + 1));

            switch (i) {
                case 0:
                    mTabHost.addTab(
                            mTabHost.newTabSpec("day1").setIndicator(mObj.getDates().get(0).getDate(), null),
                            DayOneFragment.class, bundle);
                    Log.e("Switch", "day" + (i + 1));
                    break;
                case 1:
                    mTabHost.addTab(
                            mTabHost.newTabSpec("day2").setIndicator(mObj.getDates().get(1).getDate(), null),
                            DayTwoFragment.class, bundle);
                    Log.e("Switch", "day" + (i + 1));
                    break;
                case 2:
                    mTabHost.addTab(
                            mTabHost.newTabSpec("day3").setIndicator(mObj.getDates().get(2).getDate(), null),
                            DayThreeFragment.class, bundle);
                    Log.e("Switch", "day" + (i + 1));
                    break;
                case 3:
                    mTabHost.addTab(
                            mTabHost.newTabSpec("day4").setIndicator(mObj.getDates().get(3).getDate(), null),
                            DayFourFragment.class, bundle);
                    Log.e("Switch", "day" + (i + 1));
                    break;
            }

            Log.e("SendData", (i + 1) + ". tab tehtud");
        }
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