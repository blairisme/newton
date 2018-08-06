package org.ucl.WeatherDataProvider.weather.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class provide org.ucl.WeatherDataProvider.weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class WeatherData {
    private List<Request> request;
    private List<Weather> weather;

    public List<String> getKeys(){
        List<String> keys = new ArrayList<>();
        Request request = new Request();
        keys.addAll(request.getKeys());

        Weather weather = new Weather();
        keys.addAll(weather.getKeys());

        return keys;
    }

    public List<String> getValues(){
        List<String> values = new ArrayList<>();
        for(Request re : request)
            values.addAll(re.getValues());

        for(Weather we : weather)
            values.addAll(we.getValues());
        return values;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
