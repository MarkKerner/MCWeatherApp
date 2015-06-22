package com.mark.weatherapp.Main.Handlers;

import android.util.Log;
import android.util.Xml;

import com.mark.weatherapp.Main.System.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class HandleXML {
    private final String urlString = "http://www.ilmateenistus.ee/ilma_andmed/xml/forecast.php";
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    private List<Date> dates;

    public HandleXML() {
        fetchXML();
    }

    public List<Date> getDates() {
        return dates;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String name;
        String date;
        String text = null;
        String timeOfDay = null;
        dates = new LinkedList<>();
        Date obj = null;
        boolean notLocationSpecific = true;
        boolean windData = false;
        String windLocation = null;
        String windDirection = null;
        String windSpeedMin = null;
        String windSpeedMax = null;
        String windGust = null;
        String placeLocation = null;
        String placePhenomenonNight = null;
        String placePhenomenonDay = null;
        String placeTempMin = null;
        String placeTempMax = null;
        int windLocationIndex = 0;

        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equalsIgnoreCase("forecast")) {
                            date = myParser.getAttributeValue(0);
                            obj = new Date(date);
                            dates.add(obj);

                        } else if (name.equalsIgnoreCase("night")) {
                            timeOfDay = "night";
                        } else if (name.equalsIgnoreCase("day")) {
                            timeOfDay = "day";
                        } else if (name.equalsIgnoreCase("place")) {
                            notLocationSpecific = false;
                        } else if (name.equalsIgnoreCase("wind")) {
                            windData = true;
                            windLocationIndex++;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:

                        if (notLocationSpecific && (name.equalsIgnoreCase("phenomenon")
                                || name.equalsIgnoreCase("tempmin")
                                || name.equalsIgnoreCase("tempmax"))) {
                            if (timeOfDay.equalsIgnoreCase("night")) {
                                obj.getNight().put(name, text);
                            } else if (timeOfDay.equalsIgnoreCase("day")) {
                                obj.getDay().put(name, text);
                            }
                        } else if (!notLocationSpecific) {
                            if (name.equalsIgnoreCase("name")) {
                                placeLocation = text;
                            } else if (name.equalsIgnoreCase("phenomenon")) {
                                if (timeOfDay.equalsIgnoreCase("night")) {
                                    placePhenomenonNight = text;
                                } else {
                                    placePhenomenonDay = text;
                                }
                            } else if (name.equalsIgnoreCase("tempmin")) {
                                placeTempMin = text;
                            } else if (name.equalsIgnoreCase("tempmax")) {
                                placeTempMax = text;
                            } else if (name.equalsIgnoreCase("place")) {
                                notLocationSpecific = true;

                                if (timeOfDay.equalsIgnoreCase("night")) {
                                    String[] locationData = {placePhenomenonNight, placeTempMin};
                                    obj.getLocationNight().put(placeLocation, locationData);
                                } else {
                                    String[] locationData = {placePhenomenonDay, placeTempMax};
                                    obj.getLocationDay().put(placeLocation, locationData);
                                }
                            }


                        } else if (name.equalsIgnoreCase("night") || name.equalsIgnoreCase("day")) {
                            windLocationIndex = 0;
                        } else if (windData) {
                            // TODO: Remove direction, gust.

                            if (name.equalsIgnoreCase("name")) {
                                windLocation = text;
                            } else if (name.equalsIgnoreCase("direction")) {
                                windDirection = text;
                            } else if (name.equalsIgnoreCase("speedmin")) {
                                windSpeedMin = text;
                            } else if (name.equalsIgnoreCase("speedmax")) {
                                windSpeedMax = text;
                            } else if (name.equalsIgnoreCase("gust")) {
                                windGust = text;
                            } else if (name.equalsIgnoreCase("wind")) {
                                windData = false;
                                String[] windLocationData = {windDirection, windSpeedMin, windSpeedMax, windGust};
                                Log.e("tuuleasukoht", windLocation);
                                Log.e("tuuleindex", Integer.toString(windLocationIndex));

                                if (timeOfDay.equalsIgnoreCase("night")) {
                                    obj.getWindNight().put(windLocationIndex, windLocationData);
                                } else if (timeOfDay.equalsIgnoreCase("day")) {
                                    obj.getWindDay().put(windLocationIndex, windLocationData);
                                }
                            }
                        }
                        break;
                }

                event = myParser.next();
            }


            Log.e("Päevad", dates.toString());
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);


                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    xmlFactoryObject.setValidating(false);
                    xmlFactoryObject.setFeature(Xml.FEATURE_RELAXED, true);
                    xmlFactoryObject.setNamespaceAware(true);

                    XmlPullParser myparser = xmlFactoryObject.newPullParser();
                    //myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(new InputStreamReader(stream, "UTF-8"));

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
