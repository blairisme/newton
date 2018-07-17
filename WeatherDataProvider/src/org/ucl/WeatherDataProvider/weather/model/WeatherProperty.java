package org.ucl.WeatherDataProvider.weather.model;

/**
 * Instances of this class provide org.ucl.WeatherDataProvider.weather data to the Newton system.
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
    public WeatherProperty(String city, String country, String date, String key){
        this.city = city;
        this.country = country;
        this.date = date;
        this.key = key;
    }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getDate() { return date; }
    public String getKey() { return key; }
    public void setCity(String city) { this.city = city; }
    public void setCountry(String country) { this.country = country; }
    public void setDate(String date) { this.date = date; }
    public void setKey(String key) { this.key = key; }
}
