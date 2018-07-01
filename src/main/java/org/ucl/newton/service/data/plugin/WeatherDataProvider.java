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


/**
 * Instances of this class provide weather data to the Newton system.
 *
 * @author Xiaolong Chen
 * @author Blair Butterworth
 */
public class WeatherDataProvider implements DataProvider
{
    private DataProviderObserver observer;
    private Thread thread;
    @Override
    public void start(StorageProvider storageProvider) {
        this.thread = new Thread(new GetWeatherData(storageProvider));
        thread.start();

    }

    @Override
    public void stop() {
        thread.stop();
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
