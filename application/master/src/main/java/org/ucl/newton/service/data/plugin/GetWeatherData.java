package org.ucl.newton.service.data.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.ucl.newton.service.data.model.Weather;
import org.ucl.newton.service.data.model.WeatherData;
import org.ucl.newton.service.data.sdk.StorageProvider;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class GetWeatherData implements Runnable{
    private StorageProvider storageProvider;

    public GetWeatherData(StorageProvider storageProvider){
        this.storageProvider = storageProvider;
    }
    @Override
    public void run() {

        List<WeatherProperty> weatherList = getWeatherList();

        for (WeatherProperty property : weatherList){
            String data = getDataFromWWO(property);
            displayOutput(data);
        }

    }

    // list of properties need to be configured instead of hardcode
    private List<WeatherProperty> getWeatherList() {
//        List<WeatherProperty> weatherList = fromConfiguration("weatherProperties");
        List<WeatherProperty> weatherList = new ArrayList<>();
        String city = "london";                             // required
        String country = "united kingdom";                  // can be null
        String date = "2018-07-03";                         // required and format yyyy-mm-dd
        String key = "0252e94bd710446c908123539182906";     // required

        WeatherProperty weatherProperty = new WeatherProperty(city,country,date,key);
        weatherList.add(weatherProperty);

        WeatherProperty weatherProperty2 = new WeatherProperty("tianjin","china","2018-07-03","0252e94bd710446c908123539182906");
        weatherList.add(weatherProperty2);

        return  weatherList;
    }


    private String getDataFromWWO(WeatherProperty property) {
        String url = "https://api.worldweatheronline.com/premium/v1/past-weather.ashx";
        Map<String,String> header = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        params.put("key", property.getKey());
        params.put("format","json");

        String location = locationBuilder(property.getCity(),property.getCountry());    //location format q=city(,country)
        params.put("q",location);
        String date = property.getDate();
        if (date !=null || date != "")
            params.put("date",date);
        String data = HttpUtils.doGet(url, header, params);
        return data;
    }

    private String locationBuilder(String city, String country) {
        city = city.replace(" ","+");
        if (country==null||country=="")
            return city;
        country = country.replace(" ", "+");
        return city + ","+country;
    }

    private void displayOutput(String data){
        try {
            OutputStream output = storageProvider.getOutputStream("weather");
            if(output!=null)
                output.write(data.getBytes("utf-8"));
        }catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(data);
        String dataStr = getDataStr(data);

        Gson gson = new Gson();
        WeatherData weatherData = gson.fromJson(dataStr,WeatherData.class);
        dataStr = gson.toJson(weatherData,WeatherData.class);

        System.out.println(dataStr);

    }
    private String getDataStr(String data){
        String weather = new JsonParser().parse(data).getAsJsonObject()
                                         .get("data").toString();
        return weather;
    }

}

