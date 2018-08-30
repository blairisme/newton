/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.common.concurrent.DaemonThreadFactory;
import org.ucl.newton.common.serialization.CsvSerializer;
import org.ucl.newton.sdk.plugin.*;
import org.ucl.newton.sdk.provider.BasicDataProvider;
import org.ucl.newton.sdk.provider.BasicDataSource;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.weather.model.WeatherProperty;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 * @author Blair Butterworth
 */
public class WeatherDataProvider extends BasicDataProvider
{
    private static Logger logger = LoggerFactory.getLogger(WeatherDataProvider.class);
    private List<DataSource> dataSources;
    private ScheduledExecutorService scheduler;
    private GetWeatherData handler;

    public WeatherDataProvider(){
        this.dataSources = new ArrayList<>();
        this.dataSources.add(new BasicDataSource(this, "weather.csv", "Weather data"));
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
        this.handler = new GetWeatherData(this);
    }

    @Override
    public String getIdentifier() {
        return "newton-weather";
    }

    @Override
    public PluginConfiguration getConfiguration() {
        return handler.getWeatherConfig();
    }

    @Override
    public PluginVisualization getVisualization() {
        return new BasicVisualization(
            "Weather Data Plugin",
            "Gathers weather data from World Weather Online weather data service.");
    }

    @Override
    public void setContext(PluginHostContext context) {
        InputStream input = null;
        try {
             input = context.getStorage().getInputStream("weatherList");
        }catch (IOException e){
            logger.error("Fail to load weather configuration and load default configuration instead:", e);
        }
        if (input == null)
            input = getClass().getResourceAsStream("/configuration/weatherList");
        List<WeatherProperty> weatherList = readWeatherList(input);
        WeatherConfig weatherConfig = new WeatherConfig(weatherList);
        weatherConfig.setContext(context);
        handler.setWeatherConfig(weatherConfig);
    }

    private List<WeatherProperty> readWeatherList(InputStream input) {
        List<WeatherProperty> weatherList = new ArrayList<>();
        List<String[]> properties = CsvSerializer.readCSV(input);
        for(String[] property : properties){
            WeatherProperty p = new WeatherProperty(property);
            weatherList.add(p);
        }
        return weatherList;

    }

    public DataSource getWeatherDataSource() {
        return dataSources.get(0);
    }

    @Override
    public Collection<DataSource> getDataSources() {
        return dataSources;
    }

    @Override
    public void start() {
        this.scheduler.scheduleAtFixedRate(handler, 0, 1, TimeUnit.HOURS); //run every hour
    }

    @Override
    public void stop() {
        this.scheduler.shutdown();
    }
}
