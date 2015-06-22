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

    private static final Map<String, String> sPhenomenMap =
            new HashMap<>();

    static {
        sPhenomenMap.put("Clear", "Selge");
        sPhenomenMap.put("Few clouds", "Vähene pilvisus");
        sPhenomenMap.put("Variable clouds", "Vahelduv pilvisus");
        sPhenomenMap.put("Cloudy with clear spells", "Pilves selginemistega");
        sPhenomenMap.put("Cloudy", "Pilves");
        sPhenomenMap.put("Light snow shower", "Nõrk hooglumi");
        sPhenomenMap.put("Moderate snow shower", "Mõõdukas hooglumi");
        sPhenomenMap.put("Heavy snow shower", "Tugev hooglumi");
        sPhenomenMap.put("Light shower", "Nõrk hoogvihm");
        sPhenomenMap.put("Moderate shower", "Keskmine hoogvihm");
        sPhenomenMap.put("Heavy shower", "Tugev hoogvihm");
        sPhenomenMap.put("Light rain", "Nõrk vihm");
        sPhenomenMap.put("Moderate rain", "Mõõdukas vihm");
        sPhenomenMap.put("Heavy rain", "Tugev vihm");
        sPhenomenMap.put("Risk of glaze", "Jäiteoht");
        sPhenomenMap.put("Light sleet", "Nõrk lörtsisadu");
        sPhenomenMap.put("Moderate sleet", "Mõõdukas lörtisadu");
        sPhenomenMap.put("Light snowfall", "Nõrk lumesadu");
        sPhenomenMap.put("Moderate snowfall", "Mõõdukas lumesadu");
        sPhenomenMap.put("Heavy snowfall", "Tugev lumesadu");
        sPhenomenMap.put("Snowstorm", "Üldtuisk");
        sPhenomenMap.put("Drifting snow", "Pinnatuisk");
        sPhenomenMap.put("Hail", "Rahe");
        sPhenomenMap.put("Mist", "Uduvine");
        sPhenomenMap.put("Fog", "Udu");
        sPhenomenMap.put("Thunder", "Äike");
        sPhenomenMap.put("Thunderstorm", "Äikesetorm");
    }

    private static final Map<String, String> sDatenameMap =
            new HashMap<>();

    static {
        sDatenameMap.put("01", "jaanuar");
        sDatenameMap.put("02", "veebruar");
        sDatenameMap.put("03", "märts");
        sDatenameMap.put("04", "aprill");
        sDatenameMap.put("05", "mai");
        sDatenameMap.put("06", "juuni");
        sDatenameMap.put("07", "juuli");
        sDatenameMap.put("08", "august");
        sDatenameMap.put("09", "september");
        sDatenameMap.put("10", "oktoober");
        sDatenameMap.put("11", "november");
        sDatenameMap.put("12", "detsember");

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

        CharSequence dateStringOut = dateParts[2] + ". " + sDatenameMap.get(dateParts[1]);

        return dateStringOut;
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
        weathertextViewDay = (TextView) v.findViewById(R.id.weathertextDay_TextView);
        tempViewNight = (TextView) v.findViewById(R.id.tempNight_TextView);
        weatherTextViewNight = (TextView) v.findViewById(R.id.weathertextNight_TextView);
        temptextViewDay = (TextView) v.findViewById(R.id.temptextDay_TextView);
        temptextViewNight = (TextView) v.findViewById(R.id.temptextNight_TextView);
        windViewDay = (TextView) v.findViewById(R.id.windDay_TextView);
        windViewNight = (TextView) v.findViewById(R.id.windNight_TextView);


        if (date.getDate().equalsIgnoreCase(MainActivity.sObj.getDates().get(0).getDate())) {
            userLocation = MainActivity.sLastKnownLocation;
            minDistance = userLocation.distanceTo(locationKuusiku);
            closestWindDataLocation = 1;
            if (userLocation.distanceTo(locationVMaarja) < minDistance) {
                minDistance = userLocation.distanceTo(locationVMaarja);
                closestWindDataLocation = 2;
            }
            if (userLocation.distanceTo(locationVõrtsjärv) < minDistance) {
                closestWindDataLocation = 3;
            }

            Log.e("closestLocation", Integer.toString(closestWindDataLocation));

            String[] windInfoDay = date.getWindDay().get(closestWindDataLocation);
            String[] windInfoNight = date.getWindNight().get(closestWindDataLocation);

            windViewDay.setText(windInfoDay[1] + ".." + windInfoDay[2] + " m/s");
            windViewNight.setText(windInfoNight[1] + ".." + windInfoNight[2] + " m/s");
        }

        tempmin = date.getDay().get("tempmin");
        tempmax = date.getDay().get("tempmax");
        phenomenon = sPhenomenMap.get(date.getDay().get("phenomenon"));
        tempText = NumbersToText.parse(Integer.parseInt(tempmin)) + " kuni " + NumbersToText.parse(Integer.parseInt(tempmax)) + " kraadi";

        tempViewDay.setText(tempmin + ".." + tempmax);
        temptextViewDay.setText(tempText);
        weathertextViewDay.setText(phenomenon);

        tempmin = date.getNight().get("tempmin");
        tempmax = date.getNight().get("tempmax");
        phenomenon = sPhenomenMap.get(date.getNight().get("phenomenon"));

        tempText = NumbersToText.parse(Integer.parseInt(tempmin)) + " kuni " + NumbersToText.parse(Integer.parseInt(tempmax)) + " kraadi";

        tempViewNight.setText(tempmin + ".." + tempmax);
        temptextViewNight.setText(tempText);
        weatherTextViewNight.setText(phenomenon);

        Log.e("setviewvalues", "x");
    }
}
