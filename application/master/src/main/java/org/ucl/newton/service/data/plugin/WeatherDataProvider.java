/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data.plugin;

import org.ucl.newton.service.data.sdk.DataProvider;
import org.ucl.newton.service.data.sdk.DataProviderObserver;
import org.ucl.newton.service.data.sdk.StorageProvider;

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
    public void start(StorageProvider storageProvider) {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.scheduler.scheduleAtFixedRate(new GetWeatherData(storageProvider),0,1,TimeUnit.HOURS); //run every hour
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
