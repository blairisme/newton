package org.ucl.newton.weather.model;

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

}
