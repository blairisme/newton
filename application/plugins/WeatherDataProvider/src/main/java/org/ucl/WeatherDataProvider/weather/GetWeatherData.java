/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.WeatherDataProvider.weather;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.ucl.WeatherDataProvider.HttpUtils;
import org.ucl.WeatherDataProvider.weather.model.WeatherData;
import org.ucl.WeatherDataProvider.weather.model.WeatherProperty;
import org.ucl.newton.sdk.data.DataSource;
import org.ucl.newton.sdk.data.DataStorage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class provide org.ucl.WeatherDataProvider.weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class GetWeatherData implements Runnable {
    private WeatherDataProvider provider;

    public GetWeatherData(WeatherDataProvider provider){
        this.provider = provider;
    }

    @Override
    public void run() {
        List<WeatherProperty> weatherList = getWeatherList();
        List<List<String>> listOfRecord = new ArrayList<>();
        if(!weatherList.isEmpty())
            listOfRecord.add(getHeader());
        for (WeatherProperty property : weatherList){
            String data = getDataFromWWO(property);
            if (data == null)
                continue;
            listOfRecord.add(getContent(data));
        }
        if(!listOfRecord.isEmpty()) {
            writeToOutput(listOfRecord);
            provider.notifyDataUpdated();
        }
    }

    // list of properties need to be configured instead of hardcode
    //        String city = "london";                             // required
    //        String country = "united kingdom";                  // can be null
    //        String date = "2018-07-04";                         // required and format yyyy-mm-dd
    //        String key = "0252e94bd710446c908123539182906";     // required
    private List<WeatherProperty> getWeatherList() {
        List<WeatherProperty> weatherList = new ArrayList<>();
        Path path = Paths.get(System.getProperty("user.home")).resolve(".newton");
        path = path.resolve("weather").resolve("setting");
        if(path.toFile().exists()) {
            try {
                CsvReader reader = new CsvReader(path.toString(), ',');
                reader.readHeaders();
                while (reader.readRecord()) {
                    WeatherProperty property = new WeatherProperty(reader.getValues());
                    weatherList.add(property);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //set default weather list
        if (weatherList.isEmpty()){
            WeatherProperty property = new WeatherProperty("london","united kingdom", "2018-07-04","0252e94bd710446c908123539182906");
            weatherList.add(property);
        }

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
        if (date !=null && date != "")
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
    private List<String> getHeader() {
        WeatherData weatherData = new WeatherData();
        List<String> keys = weatherData.getKeys();
        return keys;
    }
    private List<String> getContent(String data){
        String dataStr = getDataStr(data);

        Gson gson = new Gson();
        WeatherData weatherData = gson.fromJson(dataStr,WeatherData.class);
        List<String> values = weatherData.getValues();

        return values;
    }
    private void writeToOutput(List<List<String>> list){
        DataStorage storage = provider.getStorage();
        DataSource dataSource = provider.getDataSources().iterator().next();

        try (OutputStream output = storage.getOutputStream(dataSource)) {
            if (output != null) {
                CsvWriter csvWriter = new CsvWriter(output, ',', Charset.forName("utf-8"));
                for (List<String> record : list) {
                    csvWriter.writeRecord(record.toArray(new String[record.size()]));
                }
                csvWriter.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private String getDataStr(String data){
        String weather = new JsonParser().parse(data).getAsJsonObject()
                                         .get("data").toString();
        return weather;
    }

}

