package com.mark.weatherapp.Main.System;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Date implements Serializable {

    private String date;
    private Map<String, String> night;
    private Map<String, String> day;

    public Date(String date) {
        this.date = date;
        this.night = new LinkedHashMap<>();
        this.day = new LinkedHashMap<>();
    }

    public String getDate() {
        return date;
    }

    public Map<String, String> getNight() {
        return night;
    }

    public Map<String, String> getDay() {
        return day;
    }

    public String toString() {
        return "Kuupäev " + date + " Öö: " + night.toString()
                + " ,päev: " + day.toString();
    }
}
