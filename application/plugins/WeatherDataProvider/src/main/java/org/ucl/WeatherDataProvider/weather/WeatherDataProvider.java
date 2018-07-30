/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.WeatherDataProvider.weather;

import org.ucl.newton.common.concurrent.DaemonThreadFactory;
import org.ucl.newton.sdk.data.DataProvider;
import org.ucl.newton.sdk.data.DataProviderObserver;
import org.ucl.newton.sdk.data.StorageProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 * @author Blair Butterworth
 */
public class WeatherDataProvider implements DataProvider
{
    private DataProviderObserver observer;
    private ScheduledExecutorService scheduler;

    @Override
    public String getIdentifier() {
        return "newton-weather";
    }

    @Override
    public String getName() {
        return "Weather Data Plugin";
    }

    @Override
    public String getDescription() {
        return "Gathers weather data from World Weather Online weather data service.";
    }

    @Override
    public void start(StorageProvider storageProvider) {
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
        this.scheduler.scheduleAtFixedRate(new GetWeatherData(storageProvider, observer),0,1,TimeUnit.HOURS); //run every hour
    }

    @Override
    public void stop() {
        this.scheduler.shutdown();
    }

    @Override
    public void addObserver(DataProviderObserver observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver(DataProviderObserver observer) {
        this.observer = null;
    }
}
