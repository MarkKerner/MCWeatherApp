package com.mark.weatherapp.Main.GUI;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mark.weatherapp.Main.System.Date;
import com.mark.weatherapp.Main.System.NumbersToText;
import com.mark.weatherapp.R;

import java.util.HashMap;
import java.util.Map;


public class SetViewValues {

    public static final Map<String, Integer> LOCATION_MAP_REVERSED = new HashMap<>();

    static {
        LOCATION_MAP_REVERSED.put("Harku", 1);
        LOCATION_MAP_REVERSED.put("Jõhvi", 2);
        LOCATION_MAP_REVERSED.put("Tartu", 3);
        LOCATION_MAP_REVERSED.put("Pärnu", 4);
        LOCATION_MAP_REVERSED.put("Kuressaare", 5);
        LOCATION_MAP_REVERSED.put("Türi", 6);
    }

    public static final Map<Integer, String> LOCATION_MAP = new HashMap<>();

    static {
        LOCATION_MAP.put(1, "Harku");
        LOCATION_MAP.put(2, "Jõhvi");
        LOCATION_MAP.put(3, "Tartu");
        LOCATION_MAP.put(4, "Pärnu");
        LOCATION_MAP.put(5, "Kuressaare");
        LOCATION_MAP.put(6, "Türi");
    }

    private static final Map<String, String> PHENOMENON_MAP =
            new HashMap<>();

    static {
        PHENOMENON_MAP.put("Clear", "Selge");
        PHENOMENON_MAP.put("Few clouds", "Vähene pilvisus");
        PHENOMENON_MAP.put("Variable clouds", "Vahelduv pilvisus");
        PHENOMENON_MAP.put("Cloudy with clear spells", "Pilves selginemistega");
        PHENOMENON_MAP.put("Cloudy", "Pilves");
        PHENOMENON_MAP.put("Light snow shower", "Nõrk hooglumi");
        PHENOMENON_MAP.put("Moderate snow shower", "Mõõdukas hooglumi");
        PHENOMENON_MAP.put("Heavy snow shower", "Tugev hooglumi");
        PHENOMENON_MAP.put("Light shower", "Nõrk hoogvihm");
        PHENOMENON_MAP.put("Moderate shower", "Keskmine hoogvihm");
        PHENOMENON_MAP.put("Heavy shower", "Tugev hoogvihm");
        PHENOMENON_MAP.put("Light rain", "Nõrk vihm");
        PHENOMENON_MAP.put("Moderate rain", "Mõõdukas vihm");
        PHENOMENON_MAP.put("Heavy rain", "Tugev vihm");
        PHENOMENON_MAP.put("Risk of glaze", "Jäiteoht");
        PHENOMENON_MAP.put("Light sleet", "Nõrk lörtsisadu");
        PHENOMENON_MAP.put("Moderate sleet", "Mõõdukas lörtisadu");
        PHENOMENON_MAP.put("Light snowfall", "Nõrk lumesadu");
        PHENOMENON_MAP.put("Moderate snowfall", "Mõõdukas lumesadu");
        PHENOMENON_MAP.put("Heavy snowfall", "Tugev lumesadu");
        PHENOMENON_MAP.put("Snowstorm", "Üldtuisk");
        PHENOMENON_MAP.put("Drifting snow", "Pinnatuisk");
        PHENOMENON_MAP.put("Hail", "Rahe");
        PHENOMENON_MAP.put("Mist", "Uduvine");
        PHENOMENON_MAP.put("Fog", "Udu");
        PHENOMENON_MAP.put("Thunder", "Äike");
        PHENOMENON_MAP.put("Thunderstorm", "Äikesetorm");
    }

    private static final Map<String, String> DATENAME_MAP =
            new HashMap<>();

    static {
        DATENAME_MAP.put("01", "jaanuar");
        DATENAME_MAP.put("02", "veebruar");
        DATENAME_MAP.put("03", "märts");
        DATENAME_MAP.put("04", "aprill");
        DATENAME_MAP.put("05", "mai");
        DATENAME_MAP.put("06", "juuni");
        DATENAME_MAP.put("07", "juuli");
        DATENAME_MAP.put("08", "august");
        DATENAME_MAP.put("09", "september");
        DATENAME_MAP.put("10", "oktoober");
        DATENAME_MAP.put("11", "november");
        DATENAME_MAP.put("12", "detsember");

    }

    private static final double latKuusiku = 59.215230;
    private static final double lonKuusiku = 24.693471;
    private static final double latVMaarja = 59.126264;
    private static final double lonVMaarja = 26.249074;
    private static final double latVõrtsjärv = 58.277105;
    private static final double lonVõrtsjärv = 26.063440;

    private static final Location locationKuusiku = new Location("Kuusiku");

    static {
        locationKuusiku.setLatitude(59.215230);
        locationKuusiku.setLongitude(24.693471);
    }

    private static final Location locationVMaarja = new Location("Väike-Maarja");

    static {
        locationVMaarja.setLatitude(59.126264);
        locationVMaarja.setLongitude(26.249074);
    }

    private static final Location locationVõrtsjärv = new Location("Võrtsjärv");

    static {
        locationVõrtsjärv.setLatitude(58.277105);
        locationVõrtsjärv.setLongitude(26.063440);
    }


    public static CharSequence makeTitle(String dateString) {
        String[] dateParts = dateString.trim().split("-");

        CharSequence dateStringOut = dateParts[2] + ". " + DATENAME_MAP.get(dateParts[1]);

        return dateStringOut;
    }

    public static void setLocationValues(int locationIndex, View v) {

        TextView locationNameView;
        TextView phenomenonDayView;
        TextView phenomenonNightView;
        TextView tempView;
        String phenomenonDay;
        String phenomenonNight;
        String tempMin;
        String tempMax;

        locationNameView = (TextView) v.findViewById(R.id.locationName_TextView);
        phenomenonDayView = (TextView) v.findViewById(R.id.locationPhenomenonDay_TextView);
        phenomenonNightView = (TextView) v.findViewById(R.id.locationPhenomenonNight_TextView);
        tempView = (TextView) v.findViewById(R.id.locationTemperature_TextView);

        locationNameView.setText(LOCATION_MAP.get(locationIndex));
        phenomenonDay = PHENOMENON_MAP.get(MainActivity.sObj.getDates().get(0).getLocationDay().get(locationIndex)[0]);
        tempMax = MainActivity.sObj.getDates().get(0).getLocationDay().get(locationIndex)[1];
        phenomenonNight = PHENOMENON_MAP.get(MainActivity.sObj.getDates().get(0).getLocationNight().get(locationIndex)[0]);
        tempMin = MainActivity.sObj.getDates().get(0).getLocationNight().get(locationIndex)[1];


        phenomenonDayView.setText(phenomenonDay);
        phenomenonNightView.setText(phenomenonNight);
        tempView.setText(tempMin + ".." + tempMax);

        Log.e("hmm", locationNameView.getText().toString());
        Log.e("setViewvalues", "y" + locationIndex);


    }

    public static void setValues(Date date, View v) {
        TextView tempViewDay;
        TextView tempViewNight;
        TextView weathertextViewDay;
        TextView weatherTextViewNight;
        TextView temptextViewDay;
        TextView temptextViewNight;
        TextView windViewDay;
        TextView windViewNight;
        String tempmin;
        String tempmax;
        String phenomenon;
        String tempText;
        Location userLocation;
        double minDistance;
        int closestWindDataLocation;

        tempViewDay = (TextView) v.findViewById(R.id.tempDay_TextView);
        weathertextViewDay = (TextView) v.findViewById(R.id.phenomenonDay_TextView);
        tempViewNight = (TextView) v.findViewById(R.id.tempNight_TextView);
        weatherTextViewNight = (TextView) v.findViewById(R.id.phenomenonNight_TextView);
        temptextViewDay = (TextView) v.findViewById(R.id.temptextDay_TextView);
        temptextViewNight = (TextView) v.findViewById(R.id.temptextNight_TextView);
        windViewDay = (TextView) v.findViewById(R.id.windDay_TextView);
        windViewNight = (TextView) v.findViewById(R.id.windNight_TextView);


        if (date.getDate().equalsIgnoreCase(MainActivity.sObj.getDates().get(0).getDate())) {
            /**userLocation = MainActivity.sLastKnownLocation;
             minDistance = userLocation.distanceTo(locationKuusiku);
             closestWindDataLocation = 1;
             if (userLocation.distanceTo(locationVMaarja) < minDistance) {
             minDistance = userLocation.distanceTo(locationVMaarja);
             closestWindDataLocation = 2;
             }
             if (userLocation.distanceTo(locationVõrtsjärv) < minDistance) {
             closestWindDataLocation = 3;
             }

             Log.e("closestLocation", Integer.toString(closestWindDataLocation));**/


            closestWindDataLocation = 1;
            String[] windInfoDay = date.getWindDay().get(closestWindDataLocation);
            String[] windInfoNight = date.getWindNight().get(closestWindDataLocation);

            windViewDay.setText(windInfoDay[1] + ".." + windInfoDay[2] + " m/s");
            windViewNight.setText(windInfoNight[1] + ".." + windInfoNight[2] + " m/s");
        }

        tempmin = date.getDay().get("tempmin");
        tempmax = date.getDay().get("tempmax");
        phenomenon = PHENOMENON_MAP.get(date.getDay().get("phenomenon"));
        tempText = NumbersToText.parse(Integer.parseInt(tempmin)) + " kuni " + NumbersToText.parse(Integer.parseInt(tempmax)) + " kraadi";

        tempViewDay.setText(tempmin + ".." + tempmax);
        temptextViewDay.setText(tempText);
        weathertextViewDay.setText(phenomenon);

        tempmin = date.getNight().get("tempmin");
        tempmax = date.getNight().get("tempmax");
        phenomenon = PHENOMENON_MAP.get(date.getNight().get("phenomenon"));

        tempText = NumbersToText.parse(Integer.parseInt(tempmin)) + " kuni " + NumbersToText.parse(Integer.parseInt(tempmax)) + " kraadi";

        tempViewNight.setText(tempmin + ".." + tempmax);
        temptextViewNight.setText(tempText);
        weatherTextViewNight.setText(phenomenon);

        Log.e("setviewvalues", "x");
    }
}
