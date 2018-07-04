package org.ucl.newton.service.data.model;

import java.util.List;

public class WeatherData {
    private List<Request> request;
    private List<Weather> weather;

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
