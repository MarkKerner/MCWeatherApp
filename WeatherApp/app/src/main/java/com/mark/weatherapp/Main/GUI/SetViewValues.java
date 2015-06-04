package com.mark.weatherapp.Main.GUI;

import android.view.View;
import android.widget.TextView;

import com.mark.weatherapp.Main.System.Date;
import com.mark.weatherapp.Main.System.NumbersToText;
import com.mark.weatherapp.R;


public class SetViewValues {

    public static void setValues(Date date, View v) {
        TextView tempViewDay;
        TextView weatherTextViewDay;
        TextView tempViewNight;
        TextView weatherTextViewNight;
        TextView temptextViewDay;
        TextView temptextViewNight;
        String tempmin;
        String tempmax;
        String phenomenon;
        String tempText;

        tempViewDay = (TextView) v.findViewById(R.id.tempDay_TextView);
        weatherTextViewDay = (TextView) v.findViewById(R.id.weathertextDay_TextView);
        tempViewNight = (TextView) v.findViewById(R.id.tempNight_TextView);
        weatherTextViewNight = (TextView) v.findViewById(R.id.weathertextNight_TextView);
        temptextViewDay = (TextView) v.findViewById(R.id.temptextDay_TextView);
        temptextViewNight = (TextView) v.findViewById(R.id.temptextNight_TextView);

        tempmin = date.getDay().get("tempmin");
        tempmax = date.getDay().get("tempmax");
        phenomenon = MainActivity.PHENOMEN_MAP.get(date.getDay().get("phenomenon"));
        tempText = NumbersToText.parse(Integer.parseInt(tempmin)) + " kuni " + NumbersToText.parse(Integer.parseInt(tempmax)) + " kraadi";

        tempViewDay.setText(tempmin + ".." + tempmax);
        temptextViewDay.setText(tempText);
        weatherTextViewDay.setText(phenomenon);

        tempmin = date.getNight().get("tempmin");
        tempmax = date.getNight().get("tempmax");
        phenomenon = MainActivity.PHENOMEN_MAP.get(date.getNight().get("phenomenon"));

        tempText = NumbersToText.parse(Integer.parseInt(tempmin)) + " kuni " + NumbersToText.parse(Integer.parseInt(tempmax)) + " kraadi";

        tempViewNight.setText(tempmin + ".." + tempmax);
        temptextViewNight.setText(tempText);
        weatherTextViewNight.setText(phenomenon);
    }
}
