/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.weather;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.common.network.BasicHttpConnection;
import org.ucl.newton.common.serialization.CsvSerializer;
import org.ucl.newton.weather.model.WeatherData;
import org.ucl.newton.weather.model.WeatherProperty;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ucl.newton.common.network.BasicHttpConnection.statusBetween;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class GetWeatherData implements Runnable
{
    private static Logger logger = LoggerFactory.getLogger(GetWeatherData.class);

    private WeatherDataProvider provider;
    private WeatherConfig weatherConfig;
    private BasicHttpConnection connection;

    public GetWeatherData(WeatherDataProvider provider){ this(provider,new BasicHttpConnection()); }

    public GetWeatherData(WeatherDataProvider provider, BasicHttpConnection connection){
        this.provider = provider;
        this.connection = connection;
    }

    @Override
    public void run() {
        List<List<String>> listOfRecord = new ArrayList<>();
        List<WeatherProperty> weatherList = convertWeatherList(weatherConfig.getWeatherList());
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
            provider.notifyDataUpdated(provider.getWeatherDataSource());
        }
    }

    private List<WeatherProperty> convertWeatherList(List<WeatherProperty> weatherList) {
        List<WeatherProperty> newList = new ArrayList<>();
        for(WeatherProperty property : weatherList){
            WeatherProperty temp = property.getClone();
            int interval = Integer.parseInt(temp.getInterval());
            while (interval >0){
                newList.add(temp.getClone());
                temp.descendDate();
                interval = interval - 1;
            }
        }
        return newList;
    }

    // list of properties need to be configured instead of hardcode
    //        String city = "london";                             // required
    //        String country = "united kingdom";                  // can be null
    //        String date = "2018-07-04";                         // required and format yyyy-mm-dd
    //        String key = "0252e94bd710446c908123539182906";     // required



    private String getDataFromWWO(WeatherProperty property) {
        try {
            String url = "https://api.worldweatheronline.com/premium/v1/past-weather.ashx";
            Map<String, String> header = new HashMap<>();
            Map<String, String> params = new HashMap<>();
            params.put("key", property.getKey());
            params.put("format", "json");

            String location = locationBuilder(property.getCity(), property.getCountry());    //location format q=city(,country)
            params.put("q", location);
            String date = property.getDate();
            if (!Strings.isNullOrEmpty(date))
                params.put("date", date);

//        String data = HttpUtils.doGet(url, header, params);
            String data = doGet(URI.create(url), header, params);
            return data;
        }
        catch (Exception error) {
            logger.warn("Failed to download Weather data", error);
            return null;
        }
    }

    private String doGet(URI url, Map<String,String> headers, Map<String,String> parameters)
            throws IOException, URISyntaxException
    {
        connection.setAddress(url);
        connection.setHeaders(headers);
        connection.setParameters(parameters);

        try(InputStream inputStream = connection.getInputStream(statusBetween(200, 300));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
            return outputStream.toString(StandardCharsets.UTF_8.name());
        }
    }

    private String locationBuilder(String city, String country) {
        String result = city.replace(" ", "+");
        if (Strings.isNullOrEmpty(country)) {
            return result;
        }
        country = country.replace(" ", "+");
        return result + "," + country;
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
        DataSource dataSource = provider.getWeatherDataSource();
        try (OutputStream output = storage.getOutputStream(dataSource)){
            CsvSerializer.writeCSV(output,list);
        }
        catch (IOException e){
            logger.error("Failed to write weather data", e);
        }

    }
    private String getDataStr(String data){
        String weather = new JsonParser().parse(data).getAsJsonObject()
                                         .get("data").toString();
        return weather;
    }

    public void setWeatherConfig(WeatherConfig weatherConfig) {
        this.weatherConfig = weatherConfig;
    }

    public WeatherConfig getWeatherConfig() {
        return weatherConfig;
    }
}

