package org.ucl.newton.service.data.plugin.weather.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class Astronomy {
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private String moon_phase;
    private String moon_illumination;

    public List<String> getKeys(){
        List<String> keys = new ArrayList<>();
        keys.add("sunrise");
        keys.add("sunset");
        keys.add("moonrise");
        keys.add("moonset");
        keys.add("moon_phase");
        keys.add("moon_illumination");
        return keys;
    }

    public List<String> getValues(){
        List<String> values = new ArrayList<>();
        values.add(sunrise);
        values.add(sunset);
        values.add(moonrise);
        values.add(moonset);
        values.add(moon_phase);
        values.add(moon_illumination);
        return values;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) { this.sunrise = sunrise; }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getMoon_phase() {
        return moon_phase;
    }

    public void setMoon_phase(String moon_phase) {
        this.moon_phase = moon_phase;
    }

    public String getMoon_illumination() {
        return moon_illumination;
    }

    public void setMoon_illumination(String moon_illumination) {
        this.moon_illumination = moon_illumination;
    }
}
