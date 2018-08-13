/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.weather;

import org.ucl.newton.common.concurrent.DaemonThreadFactory;
import org.ucl.newton.sdk.plugin.*;
import org.ucl.newton.sdk.provider.BasicDataProvider;
import org.ucl.newton.sdk.provider.BasicDataSource;
import org.ucl.newton.sdk.provider.DataSource;

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
    private List<DataSource> dataSources;
    private ScheduledExecutorService scheduler;
    private GetWeatherData handler;

    public WeatherDataProvider(){
        this.dataSources = new ArrayList<>();
        this.dataSources.add(new BasicDataSource(this, "weather", "Weather Data"));
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
        this.handler = new GetWeatherData(this);
    }

    @Override
    public String getIdentifier() {
        return "newton-weather";
    }

    @Override
    public PluginConfiguration getConfiguration() {
        return new BasicConfiguration("weather.html");
    }

    @Override
    public PluginVisualization getVisualization() {
        return new BasicVisualization(
            "Weather Data Plugin",
            "Gathers weather data from World Weather Online weather data service.");
    }

    @Override
    public void setContext(PluginHostContext context) {
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
