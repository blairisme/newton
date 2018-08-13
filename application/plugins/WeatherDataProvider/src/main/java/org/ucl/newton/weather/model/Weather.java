package org.ucl.newton.weather.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class Weather {
    private String date;
    private List<Astronomy> astronomy;
    private String maxtempC;
    private String mintempC;
    private String totalSnow_cm;
    private String sunHour;
    private String uvIndex;

    public List<String> getKeys(){
        List<String> keys = new ArrayList<>();
        keys.add("date");
        Astronomy as = new Astronomy();
        keys.addAll(as.getKeys());
        keys.add("maxtempC");
        keys.add("mintempC");
        keys.add("totalSnow_cm");
        keys.add("sunHour");
        keys.add("uvIndex");
        return keys;
    }

    public List<String> getValues(){
        List<String> values = new ArrayList<>();
        values.add(date);
        for (Astronomy as : astronomy)
            values.addAll(as.getValues());
        values.add(maxtempC);
        values.add(mintempC);
        values.add(totalSnow_cm);
        values.add(sunHour);
        values.add(uvIndex);
        return values;
    }

}
