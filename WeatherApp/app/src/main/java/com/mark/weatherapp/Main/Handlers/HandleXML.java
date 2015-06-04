package com.mark.weatherapp.Main.Handlers;

import android.util.Log;

import com.mark.weatherapp.Main.System.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
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
                            notLocationSpecific = true;
                        } else if (name.equalsIgnoreCase("day")) {
                            timeOfDay = "day";
                            notLocationSpecific = true;
                        } else if (name.equalsIgnoreCase("place")) {
                            notLocationSpecific = false;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:

                        if (notLocationSpecific && (name.equalsIgnoreCase("phenomenon")
                                || name.equalsIgnoreCase("tempmin")
                                || name.equalsIgnoreCase("tempmax"))) {
                            if (timeOfDay == "night") {
                                obj.getNight().put(name, text);
                            } else if (timeOfDay == "day") {
                                obj.getDay().put(name, text);
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
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, "UTF_8");

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
