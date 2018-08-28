package org.ucl.newton.weather.model;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class WeatherProperty{
    private String city;
    private String country;
    private String date;
    private String key;
    private String interval;
    public WeatherProperty(String[] property){
        this.city = property[0];
        this.country = property[1];
        this.date = property[2];
        this.key = property[3];
        this.interval = property[4];
    }

    public WeatherProperty(WeatherProperty weatherProperty) {
        this.city = weatherProperty.city;
        this.country = weatherProperty.country;
        this.date = weatherProperty.date;
        this.key = weatherProperty.key;
        this.interval = weatherProperty.interval;
    }

    public String getPropertyString(){
        return city+"_"+country+"_"+date+"_"+key+"_"+interval;
    }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getDate() { return date; }
    public String getKey() { return key; }
    public String getInterval() { return interval; }

    public WeatherProperty getClone() {
        return new WeatherProperty(this);
    }

    public void descendDate() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(this.date,pos);
        long myTime = (date.getTime() / 1000) -  24 * 60 * 60;
        date.setTime(myTime * 1000);

        this.date = formatter.format(date);
    }
}
