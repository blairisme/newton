package org.ucl.newton.weather.model;

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
    public WeatherProperty() {
    }
    public WeatherProperty(String[] property){
        this.city = property[0];
        this.country = property[1];
        this.date = property[2];
        this.key = property[3];
    }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getDate() { return date; }
    public String getKey() { return key; }

}
