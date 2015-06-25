package com.mark.weatherapp.Main.System;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


public class Date implements Serializable {
    //Kuupäev stringina
    private String date;

    //Ilmainfo öö kohta Eestis
    private Map<String, String> night;

    //Ilmainfo päeva kohta Eestis
    private Map<String, String> day;

    //Tuule info öö kohta Eestis
    private Map<Integer, String[]> windNight;

    //Tuule info päeva kohta Eestis
    private Map<Integer, String[]> windDay;

    //Ilmainfo asukoha kohta öösel
    private Map<Integer, String[]> locationNight;

    //Ilmainfo asukohta kohta päeval
    private Map<Integer, String[]> locationDay;

    public Date(String date) {
        this.date = date;
        this.night = new LinkedHashMap<>();
        this.day = new LinkedHashMap<>();
        this.windNight = new LinkedHashMap<>();
        this.windDay = new LinkedHashMap<>();
        this.locationNight = new LinkedHashMap<>();
        this.locationDay = new LinkedHashMap<>();
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

    public Map<Integer, String[]> getWindNight() {
        return windNight;
    }

    public Map<Integer, String[]> getWindDay() {
        return windDay;
    }

    public Map<Integer, String[]> getLocationNight() {
        return locationNight;
    }

    public Map<Integer, String[]> getLocationDay() {
        return locationDay;
    }


    public String toString() {
        return "Kuupäev " + date + " Öö: " + night.toString()
                + " ,päev: " + day.toString() + " Öötuul: " + windNight.toString()
                + " ,päevatuul: " + windDay.toString() + " ööasukohad: " + locationNight.toString() + " päevaasukohad: " + locationDay;
    }
}
