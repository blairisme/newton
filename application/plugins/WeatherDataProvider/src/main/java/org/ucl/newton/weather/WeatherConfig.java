package org.ucl.newton.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.common.serialization.CsvSerializer;
import org.ucl.newton.sdk.plugin.BasicConfiguration;
import org.ucl.newton.sdk.plugin.PluginHostContext;
import org.ucl.newton.weather.model.WeatherProperty;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Instances of this class provide data from Fizzyo.
 *
 * @author Xiaolong Chen
 */
public class WeatherConfig extends BasicConfiguration {

    private static Logger logger = LoggerFactory.getLogger(WeatherConfig.class);
    private PluginHostContext context;
    private List<WeatherProperty> weatherList;
    public WeatherConfig(List<WeatherProperty> weatherList) {
        super("weather.html");
        this.weatherList = weatherList;
    }
    @Override
    public void setValues(Map<String, List<String>> values) {

        writeToFile();
    }

    @Override
    public Map<String, String> getValues(){
        Map<String,String> values = new HashMap<>();
        List<String> allWeatherList = new ArrayList<>();
        for (WeatherProperty property : weatherList){
            allWeatherList.add(property.getPropertyString());
        }
        values.put("allWeatherList",String.join(",",allWeatherList));
        return values;

    }
    private void writeToFile() {
        try {
            OutputStream output = context.getStorage().getOutputStream("weatherList");
            CsvSerializer.writeCSV(output,buildList());
        }catch (IOException e){
            logger.error("Fail to write weather configuration");
        }
    }

    private List<List<String>> buildList() {
        List<List<String>> list = new ArrayList<>();
        list.add(buildHead());
        list.addAll(buildContent());
        return list;
    }

    private List<String> buildHead() {
        List<String> head = new ArrayList<>();
        head.add("city");
        head.add("country");
        head.add("date");
        head.add("key");
        return head;
    }

    private List<List<String>> buildContent() {
        List<List<String>> content = new ArrayList<>();
        for (WeatherProperty property : weatherList){
            List<String> item = new ArrayList<>();
            item.add(property.getCity());
            item.add(property.getCountry());
            item.add(property.getDate());
            item.add(property.getKey());
            content.add(item);
        }
        return content;
    }

    public List<WeatherProperty> getWeatherList() {
        return weatherList;
    }

    public void setContext(PluginHostContext context) {
        this.context = context;
    }
}
