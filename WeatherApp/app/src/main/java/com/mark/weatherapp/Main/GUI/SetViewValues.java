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

    public static final Location LOCATION_KUUSIKU = new Location("Kuusiku");

    static {
        LOCATION_KUUSIKU.setLatitude(59.215230);
        LOCATION_KUUSIKU.setLongitude(24.693471);
    }

    public static final Location LOCATION_V_MAARJA = new Location("Väike-Maarja");

    static {
        LOCATION_V_MAARJA.setLatitude(59.126264);
        LOCATION_V_MAARJA.setLongitude(26.249074);
    }

    public static final Location LOCATION_VÕRTSJÄRV = new Location("Võrtsjärv");

    static {
        LOCATION_VÕRTSJÄRV.setLatitude(58.277105);
        LOCATION_VÕRTSJÄRV.setLongitude(26.063440);
    }


    public static CharSequence makeTitle(String dateString) {
        String[] dateParts = dateString.trim().split("-");

        CharSequence dateStringOut = dateParts[2] + ". " + DATENAME_MAP.get(dateParts[1]);

        return dateStringOut;
    }


    //Seab asukohtade fragmentide väärtused vastavalt spinneri valikule
    public static void setLocationValues(int locationIndex, View v) {

        TextView phenomenonDayView;
        TextView phenomenonNightView;
        TextView tempDayView;
        TextView tempNightView;
        TextView temptextDayView;
        TextView temptextNightView;
        String phenomenonDay;
        String phenomenonNight;
        String tempMin;
        String tempMax;

        phenomenonDayView = (TextView) v.findViewById(R.id.locationPhenomenonDay_TextView);
        phenomenonNightView = (TextView) v.findViewById(R.id.locationPhenomenonNight_TextView);
        tempDayView = (TextView) v.findViewById(R.id.locationTempDay_TextView);
        tempNightView = (TextView) v.findViewById(R.id.locationTempNight_TextView);
        temptextDayView = (TextView) v.findViewById(R.id.locationTemptextDay_TextView);
        temptextNightView = (TextView) v.findViewById(R.id.locationTemptextNight_TextView);

        phenomenonDay = PHENOMENON_MAP.get(MainActivity.sRSSObj.getDates().get(0).getLocationDay().get(locationIndex)[0]);
        tempMax = MainActivity.sRSSObj.getDates().get(0).getLocationDay().get(locationIndex)[1];
        phenomenonNight = PHENOMENON_MAP.get(MainActivity.sRSSObj.getDates().get(0).getLocationNight().get(locationIndex)[0]);
        tempMin = MainActivity.sRSSObj.getDates().get(0).getLocationNight().get(locationIndex)[1];


        phenomenonDayView.setText(phenomenonDay);
        phenomenonNightView.setText(phenomenonNight);
        tempDayView.setText(tempMax);
        tempNightView.setText(tempMin);
        temptextDayView.setText(NumbersToText.parse(Integer.parseInt(tempMax)) + " kraadi");
        temptextNightView.setText(NumbersToText.parse(Integer.parseInt(tempMin)) + " kraadi");

    }

    //Selle meetodi abil antakse väärtused Eesti üldistele andmetele neljal kuupäeval. Esimsele kuupäeval antakse ka tuule tugevus vastavalt GPS-i asukohale.
    public static void setValues(Date date, View v) {
        TextView tempViewDay;
        TextView tempViewNight;
        TextView weathertextViewDay;
        TextView weatherTextViewNight;
        TextView temptextViewDay;
        TextView temptextViewNight;
        TextView windViewDay;
        TextView windViewNight;
        String tempMin;
        String tempMax;
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


        if (date.getDate().equalsIgnoreCase(MainActivity.sRSSObj.getDates().get(0).getDate())) {
            userLocation = MainActivity.sLastKnownLocation;
            minDistance = userLocation.distanceTo(LOCATION_KUUSIKU);
            closestWindDataLocation = 1;
            if (userLocation.distanceTo(LOCATION_V_MAARJA) < minDistance) {
                minDistance = userLocation.distanceTo(LOCATION_V_MAARJA);
                closestWindDataLocation = 2;
            }
            if (userLocation.distanceTo(LOCATION_VÕRTSJÄRV) < minDistance) {
                closestWindDataLocation = 3;
            }

            Log.d("closestLocation", Integer.toString(closestWindDataLocation));

            String[] windInfoDay = date.getWindDay().get(closestWindDataLocation);
            String[] windInfoNight = date.getWindNight().get(closestWindDataLocation);

            windViewDay.setText(windInfoDay[1] + ".." + windInfoDay[2] + " m/s");
            windViewNight.setText(windInfoNight[1] + ".." + windInfoNight[2] + " m/s");
        }

        tempMin = date.getDay().get("tempmin");
        tempMax = date.getDay().get("tempmax");
        phenomenon = PHENOMENON_MAP.get(date.getDay().get("phenomenon"));
        tempText = NumbersToText.parse(Integer.parseInt(tempMin)) + " kuni " + NumbersToText.parse(Integer.parseInt(tempMax)) + " kraadi";

        tempViewDay.setText(tempMin + ".." + tempMax);
        temptextViewDay.setText(tempText);
        weathertextViewDay.setText(phenomenon);

        tempMin = date.getNight().get("tempmin");
        tempMax = date.getNight().get("tempmax");
        phenomenon = PHENOMENON_MAP.get(date.getNight().get("phenomenon"));

        tempText = NumbersToText.parse(Integer.parseInt(tempMin)) + " kuni " + NumbersToText.parse(Integer.parseInt(tempMax)) + " kraadi";

        tempViewNight.setText(tempMin + ".." + tempMax);
        temptextViewNight.setText(tempText);
        weatherTextViewNight.setText(phenomenon);
    }
}
